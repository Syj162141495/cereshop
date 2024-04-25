/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.tool.CereShopSeckillDAO;
import com.shop.cereshop.business.page.tool.*;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.CereShopSeckillDetailService;
import com.shop.cereshop.business.service.tool.CereShopSeckillService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWorkDetail;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereShopSeckillServiceImpl implements CereShopSeckillService {

    @Autowired
    private CereShopSeckillDAO cereShopSeckillDAO;

    @Autowired
    private CereShopSeckillDetailService cereShopSeckillDetailService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopSeckillSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        if(IntegerEnum.YES.getCode().equals(param.getIfNumber())){
            //如果限制数量
            if(EmptyUtils.isEmpty(param.getNumber())){
                throw new CoBusinessException(CoReturnFormat.PARAM_NUMBER_NOT_NULL);
            }
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopSeckill cereShopSeckill=new CereShopSeckill();
        cereShopSeckill.setCreateTime(time);
        cereShopSeckill.setShopId(param.getShopId());
        cereShopSeckill.setSeckillName(param.getSeckillName());
        cereShopSeckill.setEnableTime(param.getEnableTime());
        cereShopSeckill.setIfAdd(param.getIfAdd());
        cereShopSeckill.setIfEnable(param.getIfEnable());
        cereShopSeckill.setIfLimit(param.getIfLimit());
        cereShopSeckill.setLimitNumber(param.getLimitNumber());
        cereShopSeckill.setRemark(param.getRemark());
        cereShopSeckill.setEffectiveStart(param.getEffectiveStart());
        cereShopSeckill.setEffectiveEnd(param.getEffectiveEnd());
        cereShopSeckill.setIfNumber(param.getIfNumber());
        cereShopSeckill.setNumber(param.getNumber());
        if(TimeUtils.isBelong(param.getEffectiveStart(),param.getEffectiveEnd())){
            //如果当前时间在这个范围内,状态为进行中
            cereShopSeckill.setState(IntegerEnum.COUPON_STATE_START.getCode());
        }else {
            //如果不在,状态为未开始
            cereShopSeckill.setState(IntegerEnum.COUPON_STATE_READY.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CereShopSeckill> list=cereShopSeckillDAO.checkTime(cereShopSeckill);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //校验商品参与活动唯一
        if(!EmptyUtils.isEmpty(param.getDetails())){
            List<Long> productIds = param.getDetails().stream().map(CereShopSeckillDetail::getProductId).collect(Collectors.toList());
            //查询当前时间范围内这些商品中是否存在限时折扣活动中
            List<Long> ids=cereShopSeckillDAO.checkOtherDiscount(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在拼团活动中
            ids=cereShopSeckillDAO.checkOtherWork(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台秒杀活动中
            ids=cerePlatformSeckillService.checkPlatformSeckill(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
            ids=cerePlatformDiscountService.checkPlatformDiscount(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
            }
        }
        //插入秒杀活动数据
        cereShopSeckillDAO.insert(cereShopSeckill);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopSeckill.getState())){
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopSeckill.getEffectiveEnd()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId(),cereShopSeckill.getEffectiveEnd());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopSeckill.getState())){
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_SECKILL_IN.getCode()+"-"+cereShopSeckill.getShopSeckillId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopSeckill.getEffectiveStart()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_SECKILL_IN.getCode()+"-"+cereShopSeckill.getShopSeckillId(),cereShopSeckill.getEffectiveStart());
        }
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach(detail -> {
                detail.setShopSeckillId(cereShopSeckill.getShopSeckillId());
                detail.setNumber(param.getNumber());
                detail.setTotal(param.getNumber());
            });
            cereShopSeckillDetailService.insertBatch(param.getDetails());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"秒杀活动管理","商家端操作","新增秒杀活动",cereShopSeckill.getShopSeckillId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopSeckillUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        if(IntegerEnum.YES.getCode().equals(param.getIfNumber())){
            //如果限制数量
            if(EmptyUtils.isEmpty(param.getNumber())){
                throw new CoBusinessException(CoReturnFormat.PARAM_NUMBER_NOT_NULL);
            }
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopSeckill cereShopSeckill=new CereShopSeckill();
        cereShopSeckill.setShopSeckillId(param.getShopSeckillId());
        cereShopSeckill.setUpdateTime(time);
        cereShopSeckill.setShopId(param.getShopId());
        cereShopSeckill.setSeckillName(param.getSeckillName());
        cereShopSeckill.setEnableTime(param.getEnableTime());
        cereShopSeckill.setIfAdd(param.getIfAdd());
        cereShopSeckill.setIfEnable(param.getIfEnable());
        cereShopSeckill.setIfLimit(param.getIfLimit());
        cereShopSeckill.setLimitNumber(param.getLimitNumber());
        cereShopSeckill.setRemark(param.getRemark());
        cereShopSeckill.setEffectiveStart(param.getEffectiveStart());
        cereShopSeckill.setEffectiveEnd(param.getEffectiveEnd());
        cereShopSeckill.setIfNumber(param.getIfNumber());
        cereShopSeckill.setNumber(param.getNumber());
        if(TimeUtils.isBelong(param.getEffectiveStart(),param.getEffectiveEnd())){
            //如果当前时间在这个范围内,状态为进行中
            cereShopSeckill.setState(IntegerEnum.COUPON_STATE_START.getCode());
        }else {
            //如果不在,状态为未开始
            cereShopSeckill.setState(IntegerEnum.COUPON_STATE_READY.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CereShopSeckill> list=cereShopSeckillDAO.checkUpdateTime(cereShopSeckill);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //校验商品参与活动唯一
        if(!EmptyUtils.isEmpty(param.getDetails())){
            List<Long> productIds = param.getDetails().stream().map(CereShopSeckillDetail::getProductId).collect(Collectors.toList());
            //查询当前时间范围内这些商品中是否存在限时折扣活动中
            List<Long> ids=cereShopSeckillDAO.checkOtherDiscount(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在拼团活动中
            ids=cereShopSeckillDAO.checkOtherWork(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台秒杀活动中
            ids=cerePlatformSeckillService.checkPlatformSeckill(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
            ids=cerePlatformDiscountService.checkPlatformDiscount(productIds,cereShopSeckill.getEffectiveStart(),
                    cereShopSeckill.getEffectiveEnd(),cereShopSeckill.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
            }
        }
        //更新秒杀活动数据
        cereShopSeckillDAO.updateByPrimaryKeySelective(cereShopSeckill);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopSeckill.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId());
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopSeckill.getEffectiveEnd()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId(),cereShopSeckill.getEffectiveEnd());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopSeckill.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_SECKILL_IN.getCode()+"-"+cereShopSeckill.getShopSeckillId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_SECKILL_IN.getCode()+"-"+cereShopSeckill.getShopSeckillId());
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_SECKILL_IN.getCode()+"-"+cereShopSeckill.getShopSeckillId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopSeckill.getEffectiveStart()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_SECKILL_IN.getCode()+"-"+cereShopSeckill.getShopSeckillId(),cereShopSeckill.getEffectiveStart());
        }
        //清空商品明细
        cereShopSeckillDetailService.deleteById(cereShopSeckill.getShopSeckillId());
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach(detail -> {
                detail.setShopSeckillId(cereShopSeckill.getShopSeckillId());
                detail.setNumber(param.getNumber());
                detail.setTotal(param.getNumber());
            });
            cereShopSeckillDetailService.insertBatch(param.getDetails());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"秒杀活动管理","商家端操作","修改秒杀活动",cereShopSeckill.getShopSeckillId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long shopSeckillId, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cereShopSeckillDAO.deleteByPrimaryKey(shopSeckillId);
        //删除商品明细
        cereShopSeckillDetailService.deleteById(shopSeckillId);
        //新增日志
        cerePlatformLogService.addLog(user,"秒杀活动管理","商家端操作","删除秒杀活动",shopSeckillId,time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(Long shopSeckillId, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        CereShopSeckill cereShopSeckill=new CereShopSeckill();
        cereShopSeckill.setShopSeckillId(shopSeckillId);
        cereShopSeckill.setUpdateTime(time);
        cereShopSeckill.setState(IntegerEnum.COUPON_STATE_END.getCode());
        cereShopSeckillDAO.updateByPrimaryKeySelective(cereShopSeckill);
        //查询该活动所有待付款订单取消
        List<CereShopOrder> list=cereShopOrderService.findBySeckillId(shopSeckillId);
        if(!EmptyUtils.isEmpty(list)){
            //批量取消
            cereShopOrderService.updateBatchCancelOrder(list);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"秒杀活动管理","商家端操作","停止秒杀活动",shopSeckillId,time);
    }

    @Override
    public ShopSeckillDetail getById(Long shopSeckillId) throws CoBusinessException {
        //查询秒杀活动详情
        ShopSeckillDetail detail=cereShopSeckillDAO.getById(shopSeckillId);
        if(detail!=null){
            //设置商品明细
            detail.setProducts(cereShopSeckillDetailService.findProducts(shopSeckillId));
        }
        return detail;
    }

    @Override
    public Page getAll(ShopSeckillGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopSeckill> list=cereShopSeckillDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(shopSeckill -> {
                //设置活动内容
                shopSeckill.setContent("满"+shopSeckill.getSeckillPrice().stripTrailingZeros().toPlainString()+"元减"+
                        shopSeckill.getDownPrice().stripTrailingZeros().toPlainString()+"元");
            });
        }
        PageInfo<ShopSeckill> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getProducts(ToolProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ToolProduct> list=cereShopSeckillDAO.getProducts(param.getShopId());
        PageInfo<ToolProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public ShopSeckillData getData(ShopSeckillGetByIdParam param, Long shopId) throws CoBusinessException {
        Long shopSeckillId = param.getShopSeckillId();
        ShopSeckillData data=new ShopSeckillData();
        //设置活动名称
        data.setSeckillName(cereShopSeckillDAO.findSeckillName(shopSeckillId));
        //设置浏览量
        data.setVisit(cereShopSeckillDAO.findVisit(shopSeckillId));
        //设置支付买家数
        data.setPays(cereShopSeckillDAO.findPays(shopSeckillId,shopId));
        //设置支付转化率
        data.setConversion(0);
        //设置活动成交金额
        data.setTotal(cereShopSeckillDAO.findTotal(shopSeckillId,shopId));
        //设置商品明细
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CouponProduct> list = cereShopSeckillDAO.findDataProducts(shopSeckillId, shopId);
        PageInfo<CouponProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setProducts(page);
        return data;
    }

    @Override
    public CereShopSeckill findById(Long shopSeckillId) {
        return cereShopSeckillDAO.selectByPrimaryKey(shopSeckillId);
    }

    @Override
    public void updateState(CereShopSeckill cereShopSeckill) throws CoBusinessException {
        cereShopSeckillDAO.updateState(cereShopSeckill);
    }

    @Override
    public List<ShopSeckillDetail> getSeckills(RenovationParam param) throws CoBusinessException {
        List<ShopSeckillDetail> list=cereShopSeckillDAO.getSeckills(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(detail -> {
                //查询商品明细
                detail.setProducts(cereShopSeckillDetailService.findDistinctProducts(detail.getShopSeckillId()));
            });
        }
        return list;
    }

    @Override
    public List<Long> checkOtherDiscount(List<Long> productIs, String startTime, String endTime, Long shopId) {
        return cereShopSeckillDAO.checkOtherDiscount(productIs,startTime,endTime,shopId);
    }

    @Override
    public List<Long> checkOtherWork(List<Long> productIs, String startTime, String endTime, Long shopId) {
        return cereShopSeckillDAO.checkOtherWork(productIs,startTime,endTime,shopId);
    }

    @Override
    public void updateSeckillEndState(List<CereShopSeckill> seckills, String time) throws CoBusinessException {
        cereShopSeckillDAO.updateSeckillEndState(seckills,time);
    }
}
