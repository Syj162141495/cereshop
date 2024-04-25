/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.order.CereCollageOrderDetailDAO;
import com.shop.cereshop.business.dao.tool.CereShopGroupWorkDAO;
import com.shop.cereshop.business.page.tool.*;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.order.CereCollageOrderDetailService;
import com.shop.cereshop.business.service.order.CereCollageOrderService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.CereShopGroupWorkDetailService;
import com.shop.cereshop.business.service.tool.CereShopGroupWorkService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWorkDetail;
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
public class CereShopGroupWorkServiceImpl implements CereShopGroupWorkService {

    @Autowired
    private CereShopGroupWorkDAO cereShopGroupWorkDAO;

    @Autowired
    private CereShopGroupWorkDetailService cereShopGroupWorkDetailService;

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
    private CereCollageOrderService cereCollageOrderService;

    @Autowired
    private CereCollageOrderDetailService cereCollageOrderDetailService;

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopGroupWorkSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopGroupWork cereShopGroupWork=new CereShopGroupWork();
        cereShopGroupWork.setCreateTime(time);
        cereShopGroupWork.setShopId(param.getShopId());
        cereShopGroupWork.setGroupName(param.getGroupName());
        cereShopGroupWork.setRemark(param.getRemark());
        cereShopGroupWork.setStartTime(param.getStartTime());
        cereShopGroupWork.setEndTime(param.getEndTime());
        cereShopGroupWork.setPerson(param.getPerson());
        cereShopGroupWork.setEffectiveTime(param.getEffectiveTime());
        cereShopGroupWork.setIfLimit(param.getIfLimit());
        cereShopGroupWork.setIfEnable(param.getIfEnable());
        cereShopGroupWork.setIfAdd(param.getIfAdd());
        cereShopGroupWork.setEnableTime(param.getEnableTime());
        cereShopGroupWork.setLimitNumber(param.getLimitNumber());
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //如果当前时间在这个范围内,状态为进行中
            cereShopGroupWork.setState(IntegerEnum.COUPON_STATE_START.getCode());
        }else {
            //如果不在,状态为未开始
            cereShopGroupWork.setState(IntegerEnum.COUPON_STATE_READY.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CereShopGroupWork> list=cereShopGroupWorkDAO.checkTime(cereShopGroupWork);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //校验商品参与活动唯一
        if(!EmptyUtils.isEmpty(param.getDetails())){
            //查询当前时间范围内这些商品中是否存在秒杀活动中
            List<Long> ids=cereShopGroupWorkDAO.checkOtherSeckill(param.getDetails(),cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在限时折扣活动中
            ids=cereShopGroupWorkDAO.checkOtherDiscount(param.getDetails(),cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台秒杀活动中
            List<Long> productIds = param.getDetails().stream().map(CereShopGroupWorkDetail::getProductId).collect(Collectors.toList());
            ids=cerePlatformSeckillService.checkPlatformSeckill(productIds,cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
            ids=cerePlatformDiscountService.checkPlatformDiscount(productIds,cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
            }
        }
        //插入拼团活动数据
        cereShopGroupWorkDAO.insert(cereShopGroupWork);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopGroupWork.getState())){
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopGroupWork.getEndTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),cereShopGroupWork.getEndTime());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopGroupWork.getState())){
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_GROUP_WORK_IN.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopGroupWork.getStartTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_GROUP_WORK_IN.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),cereShopGroupWork.getStartTime());
        }
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach(detail -> {
                detail.setShopGroupWorkId(cereShopGroupWork.getShopGroupWorkId());
            });
            cereShopGroupWorkDetailService.insertBatch(param.getDetails());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"拼团活动管理","商家端操作","新增拼团活动",cereShopGroupWork.getShopGroupWorkId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopGroupWorkUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time =TimeUtils.yyMMddHHmmss();
        CereShopGroupWork cereShopGroupWork=new CereShopGroupWork();
        cereShopGroupWork.setShopGroupWorkId(param.getShopGroupWorkId());
        cereShopGroupWork.setUpdateTime(time);
        cereShopGroupWork.setGroupName(param.getGroupName());
        cereShopGroupWork.setRemark(param.getRemark());
        cereShopGroupWork.setStartTime(param.getStartTime());
        cereShopGroupWork.setEndTime(param.getEndTime());
        cereShopGroupWork.setPerson(param.getPerson());
        cereShopGroupWork.setEffectiveTime(param.getEffectiveTime());
        cereShopGroupWork.setIfLimit(param.getIfLimit());
        cereShopGroupWork.setIfEnable(param.getIfEnable());
        cereShopGroupWork.setIfAdd(param.getIfAdd());
        cereShopGroupWork.setEnableTime(param.getEnableTime());
        cereShopGroupWork.setLimitNumber(param.getLimitNumber());
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //如果当前时间在这个范围内,状态为进行中
            cereShopGroupWork.setState(IntegerEnum.COUPON_STATE_START.getCode());
        }else {
            //如果不在,状态为未开始
            cereShopGroupWork.setState(IntegerEnum.COUPON_STATE_READY.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CereShopGroupWork> list=cereShopGroupWorkDAO.checkUpdateTime(cereShopGroupWork);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //校验商品参与活动唯一
        if(!EmptyUtils.isEmpty(param.getDetails())){
            //查询当前时间范围内这些商品中是否存在秒杀活动中
            List<Long> ids=cereShopGroupWorkDAO.checkOtherSeckill(param.getDetails(),cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在限时折扣活动中
            ids=cereShopGroupWorkDAO.checkOtherDiscount(param.getDetails(),cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台秒杀活动中
            List<Long> productIds = param.getDetails().stream().map(CereShopGroupWorkDetail::getProductId).collect(Collectors.toList());
            ids=cerePlatformSeckillService.checkPlatformSeckill(productIds,cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
            }
            //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
            ids=cerePlatformDiscountService.checkPlatformDiscount(productIds,cereShopGroupWork.getStartTime(),
                    cereShopGroupWork.getEndTime(),cereShopGroupWork.getShopId());
            if(!EmptyUtils.isEmpty(ids)){
                throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
            }
        }
        //更新拼团活动数据
        cereShopGroupWorkDAO.updateByPrimaryKeySelective(cereShopGroupWork);
        if(IntegerEnum.COUPON_STATE_START.getCode().equals(cereShopGroupWork.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId());
            //如果状态为进行中,新增redis延时任务修改状态为已结束
            stringRedisService.set(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopGroupWork.getEndTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),cereShopGroupWork.getEndTime());
        }else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(cereShopGroupWork.getState())){
            //删除之前的延时任务
            stringRedisService.delete(StringEnum.SHOP_GROUP_WORK_IN.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_GROUP_WORK_IN.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId());
            //如果状态为未开始,新增redis延时任务修改状态为进行中
            stringRedisService.set(StringEnum.SHOP_GROUP_WORK_IN.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),1,
                    TimeUtils.getCountDownByTime(time,cereShopGroupWork.getStartTime()));
            //新增延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_GROUP_WORK_IN.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),cereShopGroupWork.getStartTime());
        }
        //清空商品明细数据
        cereShopGroupWorkDetailService.deleteById(cereShopGroupWork.getShopGroupWorkId());
        //新增商品明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach(detail -> {
                detail.setShopGroupWorkId(cereShopGroupWork.getShopGroupWorkId());
            });
            cereShopGroupWorkDetailService.insertBatch(param.getDetails());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"拼团活动管理","商家端操作","修改拼团活动",cereShopGroupWork.getShopGroupWorkId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long shopGroupWorkId, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        cereShopGroupWorkDAO.deleteByPrimaryKey(shopGroupWorkId);
        //删除商品明细
        cereShopGroupWorkDetailService.deleteById(shopGroupWorkId);
        //新增日志
        cerePlatformLogService.addLog(user,"拼团活动管理","商家端操作","删除拼团活动",shopGroupWorkId,time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(Long shopGroupWorkId, CerePlatformBusiness user) throws CoBusinessException,Exception {
        //校验活动是否进行中
        CereShopGroupWork cereShopGroupWork=new CereShopGroupWork();
        String time = TimeUtils.yyMMddHHmmss();
        cereShopGroupWork.setShopGroupWorkId(shopGroupWorkId);
        cereShopGroupWork.setState(IntegerEnum.COUPON_STATE_END.getCode());
        cereShopGroupWorkDAO.updateByPrimaryKeySelective(cereShopGroupWork);
        //查询所有待拼团单
        List<CereCollageOrder> list=cereShopGroupWorkDAO.findCollageOrder(shopGroupWorkId);
        if(!EmptyUtils.isEmpty(list)){
            //按拼团失败处理
            for (CereCollageOrder cereCollageOrder : list) {
                cereCollageOrder.setUpdateTime(time);
                cereCollageOrder.setState(IntegerEnum.COLLAGE_STATE_ERROR.getCode());
                cereCollageOrderService.update(cereCollageOrder);
                //查询参与该拼单的订单数据
                List<Long> ids=cereCollageOrderService.findOrderIds(cereCollageOrder.getCollageId());
                //修改拼单明细订单改为已失效,并且订单自动取消
                if(!EmptyUtils.isEmpty(ids)){
                    cereCollageOrderDetailService.updateInvalid(ids);
                    //批量取消订单
                    cereShopOrderService.cancelBatch(ids);
                }
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"拼团活动管理","商家端操作","停止拼团活动",shopGroupWorkId,time);
    }

    @Override
    public ShopGroupWorkUDetail getById(Long shopGroupWorkId) throws CoBusinessException {
        //查询拼团活动数据
        ShopGroupWorkUDetail detail=cereShopGroupWorkDAO.getById(shopGroupWorkId);
        if(detail!=null){
            //设置商品明细数据
            detail.setProducts(cereShopGroupWorkDetailService.findProducts(shopGroupWorkId));
        }
        return detail;
    }

    @Override
    public Page getProducts(ToolProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ToolProduct> list=cereShopGroupWorkDAO.getProducts(param.getShopId());
        PageInfo<ToolProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getAll(ShopGroupWorkGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopGroupWork> list=cereShopGroupWorkDAO.getAll(param);
        PageInfo<ShopGroupWork> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public ShopGroupWorkData getData(ShopGroupWorkGetByIdParam param, Long shopId) throws CoBusinessException {
        Long shopGroupWorkId = param.getShopGroupWorkId();
        ShopGroupWorkData data=new ShopGroupWorkData();
        //设置活动名称
        data.setGroupName(cereShopGroupWorkDAO.findGroupName(shopGroupWorkId));
        //设置成团数量
        data.setTotal(cereShopGroupWorkDAO.findTotal(shopGroupWorkId));
        //设置拉新转化数

        //设置活动售出金额
        data.setMoney(cereShopGroupWorkDAO.findMony(shopGroupWorkId,shopId));
        //设置商品明细
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CouponProduct> list = cereShopGroupWorkDAO.findDataProducts(shopGroupWorkId,shopId);
        PageInfo<CouponProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setProducts(page);
        return data;
    }

    @Override
    public CereShopGroupWork findById(Long shopGroupWorkId) {
        return cereShopGroupWorkDAO.selectByPrimaryKey(shopGroupWorkId);
    }

    @Override
    public void updateState(CereShopGroupWork cereShopGroupWork) throws CoBusinessException {
        cereShopGroupWorkDAO.updateState(cereShopGroupWork);
    }

    @Override
    public List<ShopGroupWorkUDetail> getGroupWorks(RenovationParam param) throws CoBusinessException {
        List<ShopGroupWorkUDetail> list=cereShopGroupWorkDAO.getGroupWorks(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(detail -> {
                //查询商品明细
                detail.setProducts(cereShopGroupWorkDetailService.findDistinctProducts(detail.getShopGroupWorkId()));
            });
        }
        return list;
    }

    @Override
    public List<CereShopGroupWork> findShopWorks() {
        return cereShopGroupWorkDAO.findShopWorks();
    }

    @Override
    public void updateWorkEndState(List<CereShopGroupWork> works, String time) throws CoBusinessException {
        cereShopGroupWorkDAO.updateWorkEndState(works,time);
    }
}
