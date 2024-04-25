/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.platformtool.CerePlatformSeckillDAO;
import com.shop.cereshop.admin.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.admin.page.seckill.*;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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
public class CerePlatformSeckillServiceImpl implements CerePlatformSeckillService {

    @Autowired
    private CerePlatformSeckillDAO cerePlatformSeckillDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(SeckillSaveParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        //如果当前时间大于活动结束时间,活动状态为活动已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getEndTime())){
            throw new CoBusinessException(CoReturnFormat.NOW_DATE_LESS_END_TIME);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformSeckill cerePlatformSeckill=new CerePlatformSeckill();
        cerePlatformSeckill.setSeckillName(param.getSeckillName());
        cerePlatformSeckill.setRemark(param.getRemark());
        cerePlatformSeckill.setSignStartTime(param.getSignStartTime());
        cerePlatformSeckill.setSignEndTime(param.getSignEndTime());
        cerePlatformSeckill.setStartTime(param.getStartTime());
        cerePlatformSeckill.setEndTime(param.getEndTime());
        cerePlatformSeckill.setIfBond(param.getIfBond());
        cerePlatformSeckill.setBondMoney(param.getBondMoney());
        cerePlatformSeckill.setSeckillMoney(param.getSeckillMoney());
        cerePlatformSeckill.setIfLimit(param.getIfLimit());
        cerePlatformSeckill.setLimitNumber(param.getLimitNumber());
        cerePlatformSeckill.setIfAdd(param.getIfAdd());
        cerePlatformSeckill.setCreateTime(time);
        cerePlatformSeckill.setUpdateTime(time);
        //校验活动开始时间必须大于报名结束时间
        if(!TimeUtils.compareTo(param.getStartTime(),param.getSignEndTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果报名开始时间大于当前时间,活动状态为报名未开始
        if(TimeUtils.compareTo(param.getSignStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_NOT_START.getCode());
        }
        //如果当前时间在报名起始时间范围内,活动状态为报名进行中
        if(TimeUtils.isBelong(param.getSignStartTime(),param.getSignEndTime())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_SIGN_ON.getCode());
        }
        //如果当前时间在报名结束时间和活动开始时间之间,活动状态为活动待开始
        if(TimeUtils.isBelong(param.getSignEndTime(),param.getStartTime())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_STAY_START.getCode());
        }
        //如果当前时间在活动起始时间范围内,活动状态为活动进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_START.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CerePlatformSeckill> seckills=cerePlatformSeckillDAO.checkTime(param);
        if(!EmptyUtils.isEmpty(seckills)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        cerePlatformSeckillDAO.insert(cerePlatformSeckill);
        //新增redis任务
        if(cerePlatformSeckill.getState().equals(IntegerEnum.ACTIVITY_NOT_START.getCode())){
            //过期时间为报名开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getSignStartTime());
        }else if(cerePlatformSeckill.getState().equals(IntegerEnum.ACTIVITY_SIGN_ON.getCode())){
            //过期时间为报名结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getSignEndTime());
        }else if(cerePlatformSeckill.getState().equals(IntegerEnum.ACTIVITY_STAY_START.getCode())){
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getStartTime());
        }else if(cerePlatformSeckill.getState().equals(IntegerEnum.ACTIVITY_START.getCode())){
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getEndTime());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"平台秒杀活动管理","平台端操作","添加平台秒杀活动",String.valueOf(cerePlatformSeckill.getSeckillId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(SeckillUpdateParam param, CerePlatformUser user) throws CoBusinessException, Exception {
        //如果当前时间大于活动结束时间,活动状态为活动已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getEndTime())){
            throw new CoBusinessException(CoReturnFormat.NOW_DATE_LESS_END_TIME);
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CerePlatformSeckill> seckills=cerePlatformSeckillDAO.checkUpdateTime(param);
        if(!EmptyUtils.isEmpty(seckills)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformSeckill cerePlatformSeckill=new CerePlatformSeckill();
        cerePlatformSeckill.setSeckillId(param.getSeckillId());
        cerePlatformSeckill.setSeckillName(param.getSeckillName());
        cerePlatformSeckill.setRemark(param.getRemark());
        cerePlatformSeckill.setSignStartTime(param.getSignStartTime());
        cerePlatformSeckill.setSignEndTime(param.getSignEndTime());
        cerePlatformSeckill.setStartTime(param.getStartTime());
        cerePlatformSeckill.setEndTime(param.getEndTime());
        cerePlatformSeckill.setIfBond(param.getIfBond());
        cerePlatformSeckill.setBondMoney(param.getBondMoney());
        cerePlatformSeckill.setSeckillMoney(param.getSeckillMoney());
        cerePlatformSeckill.setIfLimit(param.getIfLimit());
        cerePlatformSeckill.setLimitNumber(param.getLimitNumber());
        cerePlatformSeckill.setIfAdd(param.getIfAdd());
        cerePlatformSeckill.setUpdateTime(time);
        //校验活动开始时间必须大于报名结束时间
        if(!TimeUtils.compareTo(param.getStartTime(),param.getSignEndTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果报名开始时间大于当前时间,活动状态为报名未开始
        if(TimeUtils.compareTo(param.getSignStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_NOT_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+param.getSeckillId());
            //过期时间为报名开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getSignStartTime());
        }
        //如果当前时间在报名起始时间范围内,活动状态为报名进行中
        if(TimeUtils.isBelong(param.getSignStartTime(),param.getSignEndTime())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_SIGN_ON.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+param.getSeckillId());
            //过期时间为报名结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getSignEndTime());
        }
        //如果当前时间在报名结束时间和活动开始时间之间,活动状态为活动待开始
        if(TimeUtils.isBelong(param.getSignEndTime(),param.getStartTime())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_STAY_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+param.getSeckillId());
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getStartTime());
        }
        //如果当前时间在活动起始时间范围内,活动状态为活动进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+param.getSeckillId());
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+cerePlatformSeckill.getSeckillId(),param.getEndTime());
        }
        cerePlatformSeckillDAO.updateByPrimaryKeySelective(cerePlatformSeckill);
        //新增日志
        cerePlatformLogService.addLog(user,"平台秒杀活动管理","平台端操作","修改平台秒杀活动",String.valueOf(cerePlatformSeckill.getSeckillId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long seckillId, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+seckillId);
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+seckillId);
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+seckillId);
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+seckillId);
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+seckillId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+seckillId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+seckillId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+seckillId);
        //删除活动数据
        cerePlatformSeckillDAO.deleteByPrimaryKey(seckillId);
        //新增日志
        cerePlatformLogService.addLog(user,"平台秒杀活动管理","平台端操作","删除平台秒杀活动",String.valueOf(seckillId),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(Long seckillId, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+seckillId);
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+seckillId);
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+seckillId);
        stringRedisService.delete(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+seckillId);
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode()+"-"+seckillId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+seckillId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+seckillId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+seckillId);
        //更新活动状态为活动已结束
        CerePlatformSeckill cerePlatformSeckill=new CerePlatformSeckill();
        cerePlatformSeckill.setSeckillId(seckillId);
        cerePlatformSeckill.setState(IntegerEnum.ACTIVITY_END.getCode());
        cerePlatformSeckill.setUpdateTime(time);
        cerePlatformSeckillDAO.updateByPrimaryKeySelective(cerePlatformSeckill);
        //新增日志
        cerePlatformLogService.addLog(user,"平台秒杀活动管理","平台端操作","停止平台秒杀活动",String.valueOf(seckillId),time);
    }

    @Override
    public CerePlatformSeckill getById(Long seckillId) throws CoBusinessException {
        return cerePlatformSeckillDAO.selectByPrimaryKey(seckillId);
    }

    @Override
    public SeckillData getData(SeckillGetByIdParam param) throws CoBusinessException {
        Long seckillId = param.getSeckillId();
        SeckillData data=new SeckillData();
        //设置参与商家数
        data.setShops(cerePlatformSeckillDAO.findShops(seckillId));
        //设置参与商品数
        data.setProducts(cerePlatformSeckillDAO.findProducts(seckillId,null));
        //设置活动成交金额
        data.setTotal(cerePlatformSeckillDAO.findTotal(seckillId,null));
        //设置支付买家数
        data.setPays(cerePlatformSeckillDAO.findPays(seckillId));
        //设置支付转化率

        //设置支付订单数
        data.setOrders(cerePlatformSeckillDAO.findOrders(seckillId,null));
        //设置浏览量
        data.setVisit(cerePlatformSeckillDAO.findVisit(seckillId,null));
        //设置商家数据明细
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopDataDetail> shopDatas = cerePlatformSeckillDAO.findShopDatas(seckillId);
        if(!EmptyUtils.isEmpty(shopDatas)){
            shopDatas.forEach(shopDataDetail -> {
                //设置参与商品数
                shopDataDetail.setProducts(cerePlatformSeckillDAO.findProducts(seckillId,shopDataDetail.getShopId()));
                //设置访客数
                shopDataDetail.setVisit(cerePlatformSeckillDAO.findVisit(seckillId,shopDataDetail.getShopId()));
                //设置提交订单数
                shopDataDetail.setOrders(cerePlatformSeckillDAO.findSubmit(seckillId,shopDataDetail.getShopId()));
                //设置成交笔数
                shopDataDetail.setFinish(cerePlatformSeckillDAO.findOrders(seckillId,shopDataDetail.getShopId()));
                //设置成交总额
                shopDataDetail.setTotal(cerePlatformSeckillDAO.findTotal(seckillId,shopDataDetail.getShopId()));
            });
        }
        PageInfo<ShopDataDetail> pageInfo=new PageInfo<>(shopDatas);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setShopDataDetails(page);
        return data;
    }

    @Override
    public Page getAll(SeckillGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Seckill> list=cerePlatformSeckillDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(seckill -> {
                //设置参与商家数
                seckill.setShops(cerePlatformSeckillDAO.findShops(seckill.getSeckillId()));
                //设置商品数
                seckill.setProducts(cerePlatformSeckillDAO.findProducts(seckill.getSeckillId(),null));
            });
        }
        PageInfo<Seckill> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getShops(SeckillShopParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<SeckillShop> list=cerePlatformSeckillDAO.getShops(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(seckill -> {
                //设置审核次数
                seckill.setExamines(cerePlatformSeckillDAO.findExamines(seckill.getSeckillId(), seckill.getShopId()));
            });
        }
        PageInfo<SeckillShop> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getProducts(SeckillGetProductsParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<SeckillShopProduct> list=cerePlatformSeckillDAO.getProducts(param);
        PageInfo<SeckillShopProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<ShopDataDetail> findExcel(SeckillGetByIdParam param) throws CoBusinessException {
        List<com.shop.cereshop.commons.domain.shop.ShopDataDetail> shopDatas = cerePlatformSeckillDAO.findShopDatas(param.getSeckillId());
        if(!EmptyUtils.isEmpty(shopDatas)) {
            shopDatas.forEach(shopDataDetail -> {
                //设置参与商品数
                shopDataDetail.setProducts(cerePlatformSeckillDAO.findProducts(param.getSeckillId(), shopDataDetail.getShopId()));
                //设置访客数
                shopDataDetail.setVisit(cerePlatformSeckillDAO.findVisit(param.getSeckillId(), shopDataDetail.getShopId()));
                //设置提交订单数
                shopDataDetail.setOrders(cerePlatformSeckillDAO.findSubmit(param.getSeckillId(), shopDataDetail.getShopId()));
                //设置成交笔数
                shopDataDetail.setFinish(cerePlatformSeckillDAO.findOrders(param.getSeckillId(), shopDataDetail.getShopId()));
                //设置成交总额
                shopDataDetail.setTotal(cerePlatformSeckillDAO.findTotal(param.getSeckillId(), shopDataDetail.getShopId()));
            });
        }
        return shopDatas;
    }

    @Override
    public void updateSeckill(CerePlatformSeckill cerePlatformSeckill) throws CoBusinessException {
        cerePlatformSeckillDAO.updateByPrimaryKeySelective(cerePlatformSeckill);
    }

    @Override
    public List<CereActivitySign> findBySeckill(Long seckIllId) {
        return cerePlatformSeckillDAO.findBySeckill(seckIllId);
    }

    @Override
    public List<CereActivitySign> findErrorSign(Long seckIllId) {
        return cerePlatformSeckillDAO.findErrorSign(seckIllId);
    }

    @Override
    public List<CanvasPlatformSeckill> getPlatformSeckills(RenovationParam param) throws CoBusinessException {
        List<CanvasPlatformSeckill> list = cerePlatformSeckillDAO.getPlatformSeckills(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(seckill -> {
                //查询商品明细
                seckill.setProducts(cerePlatformSeckillDAO.findSeckillProduct(seckill.getSeckillId()));
            });
        }
        return list;
    }

    @Override
    public List<Long> findProductIdList(Long seckIllId) {
        return cerePlatformSeckillDAO.findProductIdList(seckIllId);
    }
}
