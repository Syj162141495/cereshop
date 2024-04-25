/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.tool.CereShopDiscountDAO;
import com.shop.cereshop.business.page.tool.*;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.CereShopDiscountDetailService;
import com.shop.cereshop.business.service.tool.CereShopDiscountService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopDiscountDetail;
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
public class CereShopDiscountServiceImpl implements CereShopDiscountService{

    @Autowired
    private CereShopDiscountDAO cereShopDiscountDAO;

    @Autowired
    private CereShopDiscountDetailService cereShopDiscountDetailService;

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
    public void save(ShopDiscountSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        if(IntegerEnum.YES.getCode().equals(param.getIfNumber())){
            //如果限制数量
            if(EmptyUtils.isEmpty(param.getNumber())){
                throw new CoBusinessException(CoReturnFormat.PARAM_NUMBER_NOT_NULL);
            }
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopDiscount cereShopDiscount=new CereShopDiscount();
        cereShopDiscount.setShopId(param.getShopId());
        cereShopDiscount.setCreateTime(time);
        cereShopDiscount.setDiscountName(param.getDiscountName());
        cereShopDiscount.setEnableTime(param.getEnableTime());
        cereShopDiscount.setEndTime(param.getEndTime());
        cereShopDiscount.setIfAdd(param.getIfAdd());
        cereShopDiscount.setIfEnable(param.getIfEnable());
        cereShopDiscount.setIfLimit(param.getIfLimit());
        cereShopDiscount.setLimitNumber(param.getLimitNumber());
        cereShopDiscount.setRemark(param.getRemark());
        cereShopDiscount.setStartTime(param.getStartTime());
        cereShopDiscount.setIfNumber(param.getIfNumber());
        cereShopDiscount.setNumber(param.getNumber());
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //如果当前时间在这个范围内,状态为进行中
            cereShopDiscount.setState(IntegerEnum.COUPON_STATE_START.getCode());
        }else {
            //如果不在,状态为未开始
            cereShopDiscount.setState(IntegerEnum.COUPON_STATE_READY.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CereShopDiscount> list=cereShopDiscountDAO.checkTime(cereShopDiscount);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //校验商品参与活动唯一
        if(!EmptyUtils.isEmpty(param.getDetails())){
            List<Long> productIds = param.getDetails().stream().map(CereShopDiscountDetail::getProductId).collect(Collectors.toList());
            //查询当前时间范围内这些商品中是否存在秒杀活动中
            List<Long> ids=cereShopDiscountDAO.checkOtherSeckill(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在拼团活动中
            ids=cereShopDiscountDAO.checkOtherWork(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台秒杀活动中
            ids=cerePlatformSeckillService.checkPlatformSeckill(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
            ids=cerePlatformDiscountService.checkPlatformDiscount(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
            }
        }
        //插入限时折扣活动数据
        cereShopDiscountDAO.insert(cereShopDiscount);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopDiscount.getState())){
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopDiscount.getEndTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId(),cereShopDiscount.getEndTime());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopDiscount.getState())){
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_DISCOUNT_IN.getCode()+"-"+cereShopDiscount.getShopDiscountId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopDiscount.getStartTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_DISCOUNT_IN.getCode()+"-"+cereShopDiscount.getShopDiscountId(),cereShopDiscount.getStartTime());
        }
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach(detail -> {
                detail.setShopDiscountId(cereShopDiscount.getShopDiscountId());
                detail.setNumber(param.getNumber());
                detail.setTotal(param.getNumber());
            });
            cereShopDiscountDetailService.insertBatch(param.getDetails());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"限时折扣活动管理","商家端操作","新增限时折扣活动",cereShopDiscount.getShopDiscountId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopDiscountUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        if(IntegerEnum.YES.getCode().equals(param.getIfNumber())){
            //如果限制数量
            if(EmptyUtils.isEmpty(param.getNumber())){
                throw new CoBusinessException(CoReturnFormat.PARAM_NUMBER_NOT_NULL);
            }
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopDiscount cereShopDiscount=new CereShopDiscount();
        cereShopDiscount.setShopDiscountId(param.getShopDiscountId());
        cereShopDiscount.setShopId(param.getShopId());
        cereShopDiscount.setCreateTime(time);
        cereShopDiscount.setDiscountName(param.getDiscountName());
        cereShopDiscount.setEnableTime(param.getEnableTime());
        cereShopDiscount.setEndTime(param.getEndTime());
        cereShopDiscount.setIfAdd(param.getIfAdd());
        cereShopDiscount.setIfEnable(param.getIfEnable());
        cereShopDiscount.setIfLimit(param.getIfLimit());
        cereShopDiscount.setLimitNumber(param.getLimitNumber());
        cereShopDiscount.setRemark(param.getRemark());
        cereShopDiscount.setStartTime(param.getStartTime());
        cereShopDiscount.setIfNumber(param.getIfNumber());
        cereShopDiscount.setNumber(param.getNumber());
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //如果当前时间在这个范围内,状态为进行中
            cereShopDiscount.setState(IntegerEnum.COUPON_STATE_START.getCode());
        }else {
            //如果不在,状态为未开始
            cereShopDiscount.setState(IntegerEnum.COUPON_STATE_READY.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CereShopDiscount> list=cereShopDiscountDAO.checkUpdateTime(cereShopDiscount);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //校验商品参与活动唯一
        if(!EmptyUtils.isEmpty(param.getDetails())){
            List<Long> productIds = param.getDetails().stream().map(CereShopDiscountDetail::getProductId).collect(Collectors.toList());
            //查询当前时间范围内这些商品中是否存在秒杀活动中
            List<Long> ids=cereShopDiscountDAO.checkOtherSeckill(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在拼团活动中
            ids=cereShopDiscountDAO.checkOtherWork(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台秒杀活动中
            ids=cerePlatformSeckillService.checkPlatformSeckill(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
            ids=cerePlatformDiscountService.checkPlatformDiscount(productIds,cereShopDiscount.getStartTime(),
                    cereShopDiscount.getEndTime(),cereShopDiscount.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
            }
        }
        //更新限时折扣活动数据
        cereShopDiscountDAO.updateByPrimaryKeySelective(cereShopDiscount);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopDiscount.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId());
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopDiscount.getEndTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId(),cereShopDiscount.getEndTime());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopDiscount.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_DISCOUNT_IN.getCode()+"-"+cereShopDiscount.getShopDiscountId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_DISCOUNT_IN.getCode()+"-"+cereShopDiscount.getShopDiscountId());
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_DISCOUNT_IN.getCode()+"-"+cereShopDiscount.getShopDiscountId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopDiscount.getStartTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_DISCOUNT_IN.getCode()+"-"+cereShopDiscount.getShopDiscountId(),cereShopDiscount.getStartTime());
        }
        //清空商品明细
        cereShopDiscountDetailService.deleteById(cereShopDiscount.getShopDiscountId());
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach(detail -> {
                detail.setShopDiscountId(cereShopDiscount.getShopDiscountId());
                detail.setNumber(param.getNumber());
                detail.setTotal(param.getNumber());
            });
            cereShopDiscountDetailService.insertBatch(param.getDetails());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"限时折扣活动管理","商家端操作","修改限时折扣活动",cereShopDiscount.getShopDiscountId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long shopDiscountId, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cereShopDiscountDAO.deleteByPrimaryKey(shopDiscountId);
        //删除商品明细
        cereShopDiscountDetailService.deleteById(shopDiscountId);
        //新增日志
        cerePlatformLogService.addLog(user,"限时折扣活动管理","商家端操作","删除限时折扣活动",shopDiscountId,time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(Long shopDiscountId, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        CereShopDiscount cereShopDiscount=new CereShopDiscount();
        cereShopDiscount.setShopDiscountId(shopDiscountId);
        cereShopDiscount.setUpdateTime(time);
        cereShopDiscount.setState(IntegerEnum.COUPON_STATE_END.getCode());
        cereShopDiscountDAO.updateByPrimaryKeySelective(cereShopDiscount);
        //查询该活动所有待付款订单取消
        List<CereShopOrder> list=cereShopOrderService.findByDiscountId(shopDiscountId);
        if(!EmptyUtils.isEmpty(list)){
            //批量取消
            cereShopOrderService.updateBatchCancelOrder(list);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"限时折扣活动管理","商家端操作","停止限时折扣活动",shopDiscountId,time);
    }

    @Override
    public ShopDiscountDetail getById(Long shopDiscountId) throws CoBusinessException {
        //查询限时折扣详情
        ShopDiscountDetail detail=cereShopDiscountDAO.getById(shopDiscountId);
        if(detail!=null){
            //设置商品明细数据
            detail.setProducts(cereShopDiscountDetailService.findProducts(shopDiscountId));
        }
        return detail;
    }

    @Override
    public Page getAll(ShopDiscountGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopDiscount> list=cereShopDiscountDAO.getAll(param);
        PageInfo<ShopDiscount> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getProducts(ToolProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ToolProduct> list=cereShopDiscountDAO.getProducts(param.getShopId());
        PageInfo<ToolProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public ShopDiscountData getData(ShopDiscountGetByIdParam param, Long shopId) throws CoBusinessException {
        Long shopDiscountId = param.getShopDiscountId();
        ShopDiscountData data=new ShopDiscountData();
        //设置活动名称
        data.setDiscountName(cereShopDiscountDAO.findDiscountName(shopDiscountId));
        //设置浏览量
        data.setVisit(cereShopDiscountDAO.findVisit(shopDiscountId));
        //设置活动付款订单数
        data.setPays(cereShopDiscountDAO.findPays(shopDiscountId,shopId));
        //设置每小时平均付款订单数

        //设置活动成交金额
        data.setTotal(cereShopDiscountDAO.findTotal(shopDiscountId,shopId));
        //设置商品明细数据
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CouponProduct> list = cereShopDiscountDAO.findDataProducts(shopDiscountId, shopId);
        PageInfo<CouponProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setProducts(page);
        return data;
    }

    @Override
    public CereShopDiscount findById(Long shopDiscountId) {
        return cereShopDiscountDAO.selectByPrimaryKey(shopDiscountId);
    }

    @Override
    public void updateState(CereShopDiscount cereShopDiscount) throws CoBusinessException {
        cereShopDiscountDAO.updateState(cereShopDiscount);
    }

    @Override
    public List<ShopDiscountDetail> getDiscounts(RenovationParam param) throws CoBusinessException {
        List<ShopDiscountDetail> list=cereShopDiscountDAO.getDiscounts(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(detail -> {
                //查询商品明细
                detail.setProducts(cereShopDiscountDetailService.findDistinctProducts(detail.getShopDiscountId()));
            });
        }
        return list;
    }

    @Override
    public List<Long> checkOtherSeckill(List<Long> productIs, String startTime, String endTime, Long shopId) {
        return cereShopDiscountDAO.checkOtherSeckill(productIs,startTime,endTime,shopId);
    }

    @Override
    public List<CereShopDiscount> findShopDiscount() {
        return cereShopDiscountDAO.findShopDiscount();
    }

    @Override
    public void updateDiscountEndState(List<CereShopDiscount> discounts, String time) throws CoBusinessException {
        cereShopDiscountDAO.updateDiscountEndState(discounts,time);
    }
}
