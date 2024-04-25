/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.timing;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.tool.ShopCrowdDetail;
import com.shop.cereshop.business.param.tool.CrowdCondition;
import com.shop.cereshop.business.service.tool.CereShopOperateService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 客户标签定时任务处理
 */
public class OperateInitializationRunnable implements Runnable{

    private CereShopOperateService cereShopOperateService;

    public OperateInitializationRunnable(CereShopOperateService cereShopOperateService){
        this.cereShopOperateService=cereShopOperateService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询当前日期在时间范围内并且计划方式为自动长期计划的所有运营计划
            List<CereShopOperate> list=cereShopOperateService.findAll();
            if(!EmptyUtils.isEmpty(list)){
                for (CereShopOperate cereShopOperate : list) {
                    //根据人群id查询人群数据
                    CereShopCrowd cereShopCrowd=cereShopOperateService.findCrowd(cereShopOperate.getShopCrowdId());
                    if(cereShopCrowd!=null){
                        List<String> userIds=null;
                        //设置已关联的客户id
                        if(!EmptyUtils.isEmpty(cereShopCrowd.getIds())){
                            userIds=EmptyUtils.getImages(cereShopCrowd.getIds());
                        }
                        //设置筛选条件对象
                        ShopCrowdDetail detail=setCondition(cereShopCrowd);
                        //排查当前是否有新的客户满足人群条件,拿到新增的客户id数组
                        if(detail!=null&&!EmptyUtils.isEmpty(detail.getConditions())){
                            List<String> ids=filter(detail.getConditions(),userIds);
                            if(!EmptyUtils.isEmpty(ids)){
                                //如果有新增的客户,即时发送消息通知
                                List<Long> idList=ids.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                                addOperateNotice(idList,cereShopOperate);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addOperateNotice(List<Long> ids,CereShopOperate cereShopOperate) throws CoBusinessException{
        //查询用户中已发送过券的人过滤掉
        List<Long> alreadys=cereShopOperateService.findAlreadyCoupon(ids,cereShopOperate.getShopOperateId());
        String time = TimeUtils.yyMMddHHmmss();
        //根据运营计划id查询优惠券数据
        List<OperateCoupon> coupons=cereShopOperateService.findCouponsById(cereShopOperate.getShopOperateId());
        if(!EmptyUtils.isEmpty(coupons)){
            int count=coupons.stream().mapToInt(OperateCoupon::getNumber).sum();
            //封装客户对应消息数据
            List<CereNotice> list = ids.stream().filter(id -> !alreadys.contains(id)).map(id -> {
                CereNotice cereNotice = new CereNotice();
                cereNotice.setBuyerUserId(id);
                cereNotice.setCreateTime(time);
                cereNotice.setShopId(cereShopOperate.getShopId());
                cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
                cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_SYSTEM.getCode());
                cereNotice.setNoticeContent("您获得" + count + "张优惠券,赶紧使用吧~");
                cereNotice.setCreateTime(time);
                return cereNotice;
            }).collect(Collectors.toList());
            //批量插入消息数据
            List<CereBuyerShopCoupon> buyerShopCoupons=new ArrayList<>();
            cereShopOperateService.insertBatchNotice(list);
            for (OperateCoupon coupon : coupons) {
                //封装客户优惠券数据
                for (Long id : ids) {
                    if(!alreadys.contains(id)){
                        for (Integer i = 0; i < coupon.getNumber(); i++) {
                            CereBuyerShopCoupon cereBuyerShopCoupon=new CereBuyerShopCoupon();
                            cereBuyerShopCoupon.setBuyerUserId(id);
                            cereBuyerShopCoupon.setShopOperateId(cereShopOperate.getShopOperateId());
                            cereBuyerShopCoupon.setShopCouponId(coupon.getShopCouponId());
                            cereBuyerShopCoupon.setCreateTime(time);
                            cereBuyerShopCoupon.setCouponName(coupon.getCouponName());
                            cereBuyerShopCoupon.setCouponType(coupon.getCouponType());
                            cereBuyerShopCoupon.setEndTime(coupon.getEffectiveEnd());
                            cereBuyerShopCoupon.setStartTime(coupon.getEffectiveStart());
                            cereBuyerShopCoupon.setFullMoney(coupon.getThreshold());
                            cereBuyerShopCoupon.setReduceMoney(coupon.getCouponContent());
                            cereBuyerShopCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
                            buyerShopCoupons.add(cereBuyerShopCoupon);
                        }
                    }
                }
            }
            //批量插入客户关联商家优惠券数据
            cereShopOperateService.insertBatchBuyerCoupon(buyerShopCoupons);
        }
    }

    private List<String> filter(List<CrowdCondition> conditions,List<String> userIds) {
        List<String> ids=null;
        for (CrowdCondition condition : conditions) {
            //查询满足条件的客户id数组
            condition.setIds(ids);
            condition.setUserIds(userIds);
            ids=cereShopOperateService.findUserByCondition(condition);
            switch (condition.getType()){
                case 2 :
                    if(!EmptyUtils.isEmpty(ids)){
                        //过滤店铺无购买的客户id
                        ids=cereShopOperateService.findNoBuy(condition);
                    }
                    break;
                case 4 :
                    if(!EmptyUtils.isEmpty(ids)){
                        //过滤店铺无下单的客户id
                        ids=cereShopOperateService.findNoOrder(condition);
                    }
                    break;
                case 8 :
                    if(!EmptyUtils.isEmpty(ids)){
                        //过滤店铺无访问的客户id
                        ids=cereShopOperateService.findNoVisit(condition);
                    }
                    break;
                default:
                    break;
            }
        }
        return ids;
    }

    private ShopCrowdDetail setCondition(CereShopCrowd cereShopCrowd) {
        ShopCrowdDetail shopCrowdDetail=new ShopCrowdDetail();
        shopCrowdDetail.setShopCrowdId(cereShopCrowd.getShopCrowdId());
        shopCrowdDetail.setShopId(cereShopCrowd.getShopId());
        shopCrowdDetail.setCrowdName(cereShopCrowd.getCrowdName());
        //设置筛选条件数组
        List<CrowdCondition> conditions=new ArrayList<>();
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopBuyYes())){
            //如果店铺有购买存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setType(1);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopBuyYes()));
            condition.setShopId(cereShopCrowd.getShopId());
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopBuyNo())){
            //如果店铺无购买存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setType(2);
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopBuyNo()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopOrderYes())){
            //如果店铺有下单存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(3);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopOrderYes()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopOrderNo())){
            //如果店铺无下单存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(4);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopOrderNo()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopAddYes())){
            //如果店铺有加购存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(5);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopAddYes()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopAddNo())){
            //如果店铺无加购存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(6);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopAddNo()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopVisitYes())){
            //如果店铺有访问存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(7);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopVisitYes()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getShopVisitNo())){
            //如果店铺无访问存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(8);
            condition.setNumber(new BigDecimal(cereShopCrowd.getShopVisitNo()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getEffectiveBuyCount())){
            //如果店铺有效购买次数存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(9);
            condition.setCalculation(cereShopCrowd.getEffectiveBuy());
            condition.setNumber(new BigDecimal(cereShopCrowd.getEffectiveBuyCount()));
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getEffectivePriceCount())){
            //如果店铺有效购买金额存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(10);
            condition.setCalculation(cereShopCrowd.getEffectivePrice());
            condition.setNumber(cereShopCrowd.getEffectivePriceCount());
            conditions.add(condition);
        }
        if(!EmptyUtils.isEmpty(cereShopCrowd.getLabelId())){
            //如果标签id存在数据
            CrowdCondition condition=new CrowdCondition();
            condition.setShopId(cereShopCrowd.getShopId());
            condition.setType(11);
            String[] split = cereShopCrowd.getLabelId().split(",");
            List<Long> labelIds=new ArrayList<>();
            if(!EmptyUtils.isEmpty(split)){
                for (String s : split) {
                    labelIds.add(Long.parseLong(s));
                }
            }
            condition.setLabelIds(labelIds);
            conditions.add(condition);
        }
        shopCrowdDetail.setConditions(conditions);
        return shopCrowdDetail;
    }
}
