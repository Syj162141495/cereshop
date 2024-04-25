/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.tool.CereShopCouponDAO;
import com.shop.cereshop.business.page.canvas.ProductCoupon;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.tool.ShopCoupon;
import com.shop.cereshop.business.page.tool.ShopCouponData;
import com.shop.cereshop.business.page.tool.ShopCouponDetail;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.CereShopCouponDetailService;
import com.shop.cereshop.business.service.tool.CereShopCouponService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopCoupon;
import com.shop.cereshop.commons.domain.tool.CereShopCouponDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereShopCouponServiceImpl implements CereShopCouponService {

    @Autowired
    private CereShopCouponDAO cereShopCouponDAO;

    @Autowired
    private CereShopCouponDetailService cereShopCouponDetailService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopCouponSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        List<Long> ids=null;
        if(IntegerEnum.APPLY_TYPE_ALL.getCode().equals(param.getApplyType())){
            //如果是全部商品,查询本店所有有效商品id
            ids=cereShopCouponDAO.findProductIdsByShopId(param.getShopId());
        }else if(IntegerEnum.APPLY_TYPE_YES.getCode().equals(param.getApplyType())){
            //如果是指定商品可用
            ids=param.getIds();
        }else {
            //如果是指定商品不可用,查询本店所有不在这个范围内的有效商品id
            ids=cereShopCouponDAO.findProductIdsByShopIdAndIds(param.getShopId(),param.getIds());
        }
        CereShopCoupon cereShopCoupon=new CereShopCoupon();
        cereShopCoupon.setApplyType(param.getApplyType());
        cereShopCoupon.setCouponContent(param.getCouponContent());
        cereShopCoupon.setCouponName(param.getCouponName());
        cereShopCoupon.setCouponType(param.getCouponType());
        cereShopCoupon.setCreateTime(time);
        cereShopCoupon.setEffectiveDay(param.getEffectiveDay());
        cereShopCoupon.setEffectiveEnd(param.getEffectiveEnd());
        cereShopCoupon.setEffectiveStart(param.getEffectiveStart());
        cereShopCoupon.setFrequency(param.getFrequency());
        cereShopCoupon.setNumber(param.getNumber());
        cereShopCoupon.setStockNumber(param.getNumber());
        cereShopCoupon.setReceiveType(param.getReceiveType());
        cereShopCoupon.setRemark(param.getRemark());
        cereShopCoupon.setShopId(param.getShopId());
        cereShopCoupon.setIfAdd(param.getIfAdd());
        //优惠券状态为进行中
        cereShopCoupon.setState(IntegerEnum.COUPON_STATE_START.getCode());
        if(IntegerEnum.TIME_TYPE_CHANGE.getCode().equals(param.getTimeType())){
            //如果是当日起几天内可用,计算截止时间
            cereShopCoupon.setEffectiveStart(time);
            cereShopCoupon.setEffectiveEnd(TimeUtils.getMoreDayAfter(time,param.getEffectiveDay()));
        }else {
            //如果是固定时间,判断当前时间是否在时间段范围内
            if(!TimeUtils.isBelong(cereShopCoupon.getEffectiveStart(),cereShopCoupon.getEffectiveEnd())){
                //如果不在,优惠券状态为未开始
                cereShopCoupon.setState(IntegerEnum.COUPON_STATE_READY.getCode());
            }
        }
        cereShopCoupon.setThreshold(param.getThreshold());
        cereShopCoupon.setTimeType(param.getTimeType());
        //插入店铺优惠券数据
        cereShopCouponDAO.insert(cereShopCoupon);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopCoupon.getState())){
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopCoupon.getEffectiveEnd()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId(),cereShopCoupon.getEffectiveEnd());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopCoupon.getState())){
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_COUPON_IN.getCode()+"-"+cereShopCoupon.getShopCouponId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopCoupon.getEffectiveStart()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_COUPON_IN.getCode()+"-"+cereShopCoupon.getShopCouponId(),cereShopCoupon.getEffectiveStart());
        }
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(ids)){
            List<CereShopCouponDetail> collect = ids.stream().map(id -> {
                CereShopCouponDetail detail = new CereShopCouponDetail();
                detail.setShopCouponId(cereShopCoupon.getShopCouponId());
                detail.setProductId(id);
                return detail;
            }).collect(Collectors.toList());
            cereShopCouponDetailService.insertBatch(collect);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"优惠券管理","商家端操作","新增优惠券",cereShopCoupon.getShopCouponId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopCouponUpdateParam param, CerePlatformBusiness user) throws CoBusinessException, Exception {
        String time = TimeUtils.yyMMddHHmmss();
        List<Long> ids=new ArrayList<>();
        if(IntegerEnum.APPLY_TYPE_ALL.getCode().equals(param.getApplyType())){
            //如果是全部商品,查询本店所有有效商品id
            ids=cereShopCouponDAO.findProductIdsByShopId(param.getShopId());
        }else if(IntegerEnum.APPLY_TYPE_YES.getCode().equals(param.getApplyType())){
            //如果是指定商品可用
            ids=param.getIds();
        }else {
            //如果是指定商品不可用,查询本店所有不在这个范围内的有效商品id
            ids=cereShopCouponDAO.findProductIdsByShopIdAndIds(param.getShopId(),param.getIds());
        }
        CereShopCoupon cereShopCoupon=new CereShopCoupon();
        cereShopCoupon.setShopCouponId(param.getShopCouponId());
        cereShopCoupon.setUpdateTime(time);
        cereShopCoupon.setApplyType(param.getApplyType());
        cereShopCoupon.setCouponContent(param.getCouponContent());
        cereShopCoupon.setCouponName(param.getCouponName());
        cereShopCoupon.setCouponType(param.getCouponType());
        cereShopCoupon.setCreateTime(time);
        cereShopCoupon.setEffectiveDay(param.getEffectiveDay());
        cereShopCoupon.setEffectiveEnd(param.getEffectiveEnd());
        cereShopCoupon.setEffectiveStart(param.getEffectiveStart());
        cereShopCoupon.setFrequency(param.getFrequency());
        cereShopCoupon.setNumber(param.getNumber());
        cereShopCoupon.setStockNumber(param.getNumber());
        cereShopCoupon.setReceiveType(param.getReceiveType());
        cereShopCoupon.setRemark(param.getRemark());
        cereShopCoupon.setShopId(param.getShopId());
        cereShopCoupon.setIfAdd(param.getIfAdd());
        //优惠券状态为进行中
        cereShopCoupon.setState(IntegerEnum.COUPON_STATE_START.getCode());
        if(IntegerEnum.TIME_TYPE_CHANGE.getCode().equals(param.getTimeType())){
            //如果是当日起几天内可用,计算截止时间
            cereShopCoupon.setEffectiveStart(time);
            cereShopCoupon.setEffectiveEnd(TimeUtils.getMoreDayAfter(time,param.getEffectiveDay()));
        }else {
            //如果是固定时间,判断当前时间是否在时间段范围内
            if(!TimeUtils.isBelong(cereShopCoupon.getEffectiveStart(),cereShopCoupon.getEffectiveEnd())){
                //如果不在,优惠券状态为未开始
                cereShopCoupon.setState(IntegerEnum.COUPON_STATE_READY.getCode());
            }
        }
        cereShopCoupon.setThreshold(param.getThreshold());
        cereShopCoupon.setTimeType(param.getTimeType());
        //更新店铺优惠券数据
        cereShopCouponDAO.updateByPrimaryKeySelective(cereShopCoupon);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopCoupon.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId());
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopCoupon.getEffectiveEnd()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId(),cereShopCoupon.getEffectiveEnd());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopCoupon.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_COUPON_IN.getCode()+"-"+cereShopCoupon.getShopCouponId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_COUPON_IN.getCode()+"-"+cereShopCoupon.getShopCouponId());
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_COUPON_IN.getCode()+"-"+cereShopCoupon.getShopCouponId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopCoupon.getEffectiveStart()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_COUPON_IN.getCode()+"-"+cereShopCoupon.getShopCouponId(),cereShopCoupon.getEffectiveStart());
        }
        //清空商品明细数据
        cereShopCouponDetailService.deleteById(cereShopCoupon.getShopCouponId());
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(ids)){
            List<CereShopCouponDetail> collect = ids.stream().map(id -> {
                CereShopCouponDetail detail = new CereShopCouponDetail();
                detail.setShopCouponId(cereShopCoupon.getShopCouponId());
                detail.setProductId(id);
                return detail;
            }).collect(Collectors.toList());
            cereShopCouponDetailService.insertBatch(collect);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"优惠券管理","商家端操作","修改优惠券",cereShopCoupon.getShopCouponId(),time);
    }

    @Override
    public ShopCouponDetail getById(Long shopCouponId) throws CoBusinessException {
        ShopCouponDetail detail=cereShopCouponDAO.getById(shopCouponId);
        if(detail!=null){
            //查询商品明细数据
            detail.setProducts(cereShopCouponDetailService.findProducts(shopCouponId));
        }
        return detail;
    }

    @Override
    public Page getAll(ShopCouponGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopCoupon> list=cereShopCouponDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            //设置内容描述
            list.forEach(shopCoupon -> {
                if(IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(shopCoupon.getCouponType())){
                    //满减
                    if(EmptyUtils.isEmptyBigdecimal(shopCoupon.getThreshold())){
                        //如果是无门槛
                        shopCoupon.setContent("无门槛减"+shopCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                    }else {
                        //满减
                        shopCoupon.setContent("满"+shopCoupon.getThreshold().stripTrailingZeros().toPlainString()+"元"
                                +shopCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                    }
                }else {
                    //折扣
                    shopCoupon.setContent("打"+shopCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"折");
                }
            });
        }
        PageInfo<ShopCoupon> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long shopCouponId, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cereShopCouponDAO.deleteByPrimaryKey(shopCouponId);
        //删除商品明细
        cereShopCouponDetailService.deleteById(shopCouponId);
        //新增日志
        cerePlatformLogService.addLog(user,"优惠券管理","商家端操作","删除优惠券",shopCouponId,time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(Long shopCouponId, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        CereShopCoupon cereShopCoupon=new CereShopCoupon();
        cereShopCoupon.setShopCouponId(shopCouponId);
        cereShopCoupon.setState(IntegerEnum.COUPON_STATE_END.getCode());
        cereShopCoupon.setUpdateTime(time);
        cereShopCouponDAO.updateByPrimaryKeySelective(cereShopCoupon);
        //新增日志
        cerePlatformLogService.addLog(user,"优惠券管理","商家端操作","停止优惠券",shopCouponId,time);
    }

    @Override
    public Page getProducts(ToolProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ToolProduct> list=cereShopCouponDAO.getProducts(param.getShopId());
        PageInfo<ToolProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<OperateCoupon> selectCoupon(OperateCouponParam param) throws CoBusinessException {
        List<OperateCoupon> list = cereShopCouponDAO.selectCoupon(param);
        if(!EmptyUtils.isEmpty(list)){
            //设置内容描述
            list.forEach(shopCoupon -> {
                if(EmptyUtils.isEmptyBigdecimal(shopCoupon.getThreshold())){
                    //如果是无门槛
                    shopCoupon.setContent("无门槛减"+shopCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                }else {
                    //满减
                    shopCoupon.setContent("满"+shopCoupon.getThreshold().stripTrailingZeros().toPlainString()+"元"
                            +shopCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                }
            });
        }
        return list;
    }

    @Override
    public ShopCouponData getData(Long shopCouponId,Long shopId) throws CoBusinessException {
        ShopCouponData data=new ShopCouponData();
        CereShopCoupon cereShopCoupon=cereShopCouponDAO.selectByPrimaryKey(shopCouponId);
        //设置活动名称
        data.setCouponName(cereShopCouponDAO.findCouponName(shopCouponId));
        //当前日期年月日
        String time = TimeUtils.today();
        //用券成交总额
        data.setTotal(cereShopCouponDAO.findTotal(shopCouponId,shopId,time));
        //设置使用优惠券总额
        data.setUseMoney(cereShopCouponDAO.findUseMoney(shopCouponId,time));
        //设置购买商品总数
        data.setCount(cereShopCouponDAO.findCount(shopCouponId,shopId,time));
        //设置商品明细数据
        data.setProducts(cereShopCouponDAO.findDataProducts(shopCouponId, shopId,time));
        //设置优惠券总数
        data.setCouponTotal(cereShopCoupon.getNumber());
        //设置领取数
        data.setReceived(cereShopCoupon.getNumber() - cereShopCoupon.getStockNumber());
        return data;
    }

    @Override
    public CereShopCoupon findById(Long shopCouponId) {
        return cereShopCouponDAO.selectByPrimaryKey(shopCouponId);
    }

    @Override
    public void updateState(CereShopCoupon cereShopCoupon) throws CoBusinessException {
        cereShopCouponDAO.updateState(cereShopCoupon);
    }

    @Override
    public void updateBuyerCouponState(Long shopCouponId) throws CoBusinessException {
        cereShopCouponDAO.updateBuyerCouponState(shopCouponId);
    }

    @Override
    public List<CereShopCoupon> findAllByShopId(Long shopId) {
        return cereShopCouponDAO.findAllByShopId(shopId);
    }

    @Override
    public Page getShopCoupons(CanvasCouponParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ProductCoupon> list=cereShopCouponDAO.getShopCoupons(param);
        if(!EmptyUtils.isEmpty(list)){
            for (ProductCoupon coupon : list) {
                if(IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(coupon.getCouponType())){
                    //满减设置内容
                    if(!EmptyUtils.isEmptyBigdecimal(coupon.getThreshold())){
                        coupon.setContent("满"+coupon.getThreshold().stripTrailingZeros().toPlainString()+"减"
                                +coupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                    }else {
                        coupon.setContent("无门槛减"
                                +coupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                    }
                }else {
                    //折扣设置内容
                    if(!EmptyUtils.isEmptyBigdecimal(coupon.getThreshold())){
                        coupon.setContent("满"+coupon.getThreshold().stripTrailingZeros().toPlainString()+"元"
                                +coupon.getCouponContent().stripTrailingZeros().toPlainString()+"折");
                    }else {
                        coupon.setContent("无门槛"
                                +coupon.getCouponContent().stripTrailingZeros().toPlainString()+"折");
                    }
                }
            }
        }
        PageInfo<ProductCoupon> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
