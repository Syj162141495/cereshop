/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.activity.CereActivitySignDAO;
import com.shop.cereshop.admin.page.finance.BondCount;
import com.shop.cereshop.admin.page.finance.ShopBond;
import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.finance.BondParam;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.activity.CereActivitySignService;
import com.shop.cereshop.admin.service.pay.CerePayLogService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CereActivitySignServiceImpl implements CereActivitySignService {

    @Autowired
    private CereActivitySignDAO cereActivitySignDAO;

    @Autowired
    private CerePayLogService cerePayLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Override
    public void update(CereActivitySign cereActivitySign) throws CoBusinessException {
        cereActivitySignDAO.updateByPrimaryKeySelective(cereActivitySign);
    }

    @Override
    public CereActivitySign findBySignId(Long signId) {
        return cereActivitySignDAO.findBySignId(signId);
    }

    @Override
    public BondCount getAllBond(BondParam param) throws CoBusinessException {
        BondCount count=new BondCount();
        //查询保证金总额
        count.setTotal(cereActivitySignDAO.findBondTotal());
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopBond> list=cereActivitySignDAO.getAllBond(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(shopBond -> {
                //拼接BZJ-报名id查询交易流水号
                shopBond.setTransactionId(cereActivitySignDAO.findTransactionId("BZJ-"+shopBond.getSignId()+"-"));
                //根据保证金状态设置显示缴纳时间/退保时间
                if(IntegerEnum.BOND_REFUND.getCode().equals(shopBond.getBondState())){
                    shopBond.setTime(shopBond.getReturnTime());
                }else {
                    shopBond.setTime(shopBond.getPaymentTime());
                }
            });
        }
        PageInfo<ShopBond> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        count.setPage(page);
        return count;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundBond(String[] split, String transaction_id, String orderNo,int signType) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //保证金退款业务处理
        //根据店铺id和活动id查询报名数据
        CereActivitySign cereActivitySign=cereActivitySignDAO.findBySignId(Long.parseLong(split[1]));
        if(cereActivitySign!=null){
            //修改保证金状态为已退回
            cereActivitySign.setUpdateTime(time);
            cereActivitySign.setReturnTime(time);
            cereActivitySign.setBondState(IntegerEnum.BOND_REFUND.getCode());
            cereActivitySignDAO.updateByPrimaryKeySelective(cereActivitySign);
            //查询支付流水数据
            String orderFormid="";
            if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(signType)){
                //平台优惠券
                orderFormid="YHQBZJ-"+cereActivitySign.getSignId();
            }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(signType)){
                //平台秒杀
                orderFormid="MSBZJ-"+cereActivitySign.getSignId();
            }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(signType)){
                //平台限时折扣
                orderFormid="XSZKBZJ-"+cereActivitySign.getSignId();
            }
            CerePayLog payLog=cerePayLogService.findByOrderFormid(orderFormid);
            if(payLog!=null){
                //新增退款流水
                CerePayLog cerePayLog=new CerePayLog();
                cerePayLog.setCreateTime(time);
                cerePayLog.setOrderFormid(orderNo);
                cerePayLog.setOutTradeNo(orderNo);
                cerePayLog.setOutRefundNo(payLog.getOutRefundNo());
                cerePayLog.setTransactionId(transaction_id);
                cerePayLog.setRefundFee(payLog.getRefundFee());
                cerePayLog.setPaymentMode(IntegerEnum.ORDER_PAY_WX.getCode());
                cerePayLog.setShopId(payLog.getShopId());
                cerePayLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
                cerePayLog.setTotalFee(payLog.getTotalFee());
                cerePayLog.setUserId(payLog.getUserId());
                cerePayLog.setRemark(payLog.getOrderFormid()+"保证金退款"+payLog.getRefundFee()+"元");
                cerePayLogService.insert(cerePayLog);
                //清除延时任务和延时记录
                //查询当前活动是否为进行中
                Integer state = cereActivitySignDAO.findActivityState(cereActivitySign.getActivityId());
                if(IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode().equals(cereActivitySign.getState())){
                    //如果是报名成功说明退款是走的活动结束延时任务
                    stringRedisService.delete(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+signType);
                    cereRedisKeyServcice.deleteByKey(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+signType);
                }else {
                    //报名失败退款
                    if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
                        //如果是活动进行中说明当前退款是走的活动刚开始延时任务退款
                        stringRedisService.delete(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+signType);
                        cereRedisKeyServcice.deleteByKey(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+signType);
                    }else {
                        //报名失败
                        stringRedisService.delete(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+split[1]+"-"+signType);
                        cereRedisKeyServcice.deleteByKey(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+split[1]+"-"+signType);
                    }
                }
            }
        }
    }
}
