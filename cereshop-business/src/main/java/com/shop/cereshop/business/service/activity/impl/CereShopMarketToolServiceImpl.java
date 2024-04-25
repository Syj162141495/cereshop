/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.shop.cereshop.business.dao.activity.CereShopMarketToolDAO;
import com.shop.cereshop.business.page.activity.MarketTool;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.activity.CereBuyerToolService;
import com.shop.cereshop.business.service.activity.CereShopMarketToolDetailService;
import com.shop.cereshop.business.service.activity.CereShopMarketToolService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereShopMarketTool;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
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
public class CereShopMarketToolServiceImpl implements CereShopMarketToolService {

    @Autowired
    private CereShopMarketToolDAO cereShopMarketToolDAO;

    @Autowired
    private CereShopMarketToolDetailService cereShopMarketToolDetailService;

    @Autowired
    private CereBuyerToolService cereBuyerToolService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ToolSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //新增营销工具
        CereShopMarketTool cereShopMarketTool=new CereShopMarketTool();
        cereShopMarketTool.setCreateTime(time);
        cereShopMarketTool.setContent(param.getContent());
        cereShopMarketTool.setDiscountMode(param.getDiscountMode());
        cereShopMarketTool.setDiscountProgramme(param.getDiscountProgramme());
        cereShopMarketTool.setEndTime(param.getEndTime());
        cereShopMarketTool.setStartTime(param.getStartTime());
        cereShopMarketTool.setShopId(param.getShopId());
        cereShopMarketTool.setImage(param.getImage());
        cereShopMarketTool.setToolName(param.getToolName());
        cereShopMarketTool.setToolType(param.getToolType());
        cereShopMarketTool.setToolNumber(param.getToolNumber());
        cereShopMarketTool.setThreshold(param.getThreshold());
        cereShopMarketTool.setTrialProduct(param.getTrialProduct());
        //判断当前时间是否在活动起始时间范围内
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //如果在,状态为进行中
            cereShopMarketTool.setState(IntegerEnum.TOOL_HAND.getCode());
        }else {
            cereShopMarketTool.setState(IntegerEnum.TOOL_NOT_START.getCode());
        }
        cereShopMarketToolDAO.insert(cereShopMarketTool);
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //设置redis任务
            stringRedisService.set(StringEnum.SHOP_TOOL_END.getCode()+"-"+cereShopMarketTool.getToolId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cereShopMarketTool.getEndTime()));
            //新增redis延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_TOOL_END.getCode()+"-"+cereShopMarketTool.getToolId(),cereShopMarketTool.getEndTime());
        }else {
            //设置redis任务
            stringRedisService.set(StringEnum.SHOP_TOOL_IN.getCode()+"-"+cereShopMarketTool.getToolId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cereShopMarketTool.getStartTime()));
            //新增redis延时任务记录
            cereRedisKeyServcice.add(StringEnum.SHOP_TOOL_IN.getCode()+"-"+cereShopMarketTool.getToolId(),cereShopMarketTool.getStartTime());
        }
        //新增优惠方案明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            param.getDetails().forEach((detail -> {
                detail.setToolId(cereShopMarketTool.getToolId());
            }));
        }
        //批量插入方案明细数据
        cereShopMarketToolDetailService.insertBatch(param.getDetails());
        //新增日志
        cerePlatformLogService.addLog(user,"营销工具管理","商户端操作","添加营销工具",cereShopMarketTool.getToolId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ToolUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //新增营销工具
        CereShopMarketTool cereShopMarketTool=new CereShopMarketTool();
        cereShopMarketTool.setUpdateTime(time);
        cereShopMarketTool.setContent(param.getContent());
        cereShopMarketTool.setDiscountMode(param.getDiscountMode());
        cereShopMarketTool.setDiscountProgramme(param.getDiscountProgramme());
        cereShopMarketTool.setEndTime(param.getEndTime());
        cereShopMarketTool.setStartTime(param.getStartTime());
        cereShopMarketTool.setShopId(param.getShopId());
        cereShopMarketTool.setImage(param.getImage());
        cereShopMarketTool.setToolName(param.getToolName());
        cereShopMarketTool.setToolType(param.getToolType());
        cereShopMarketTool.setToolNumber(param.getToolNumber());
        cereShopMarketTool.setThreshold(param.getThreshold());
        cereShopMarketTool.setTrialProduct(param.getTrialProduct());
        cereShopMarketTool.setToolId(param.getToolId());
        //判断当前时间是否在活动起始时间范围内
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            //如果在,状态为进行中
            cereShopMarketTool.setState(IntegerEnum.TOOL_HAND.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.SHOP_TOOL_END.getCode()+"-"+param.getToolId());
            //设置redis任务
            stringRedisService.set(StringEnum.SHOP_TOOL_END.getCode()+"-"+cereShopMarketTool.getToolId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cereShopMarketTool.getEndTime()));
            //更新redis延时任务记录
            cereRedisKeyServcice.updateByKey(StringEnum.SHOP_TOOL_END.getCode()+"-"+cereShopMarketTool.getToolId(),cereShopMarketTool.getEndTime());
        }else {
            cereShopMarketTool.setState(IntegerEnum.TOOL_NOT_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.SHOP_TOOL_IN.getCode()+"-"+param.getToolId());
            //设置redis任务
            stringRedisService.set(StringEnum.SHOP_TOOL_IN.getCode()+"-"+cereShopMarketTool.getToolId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cereShopMarketTool.getStartTime()));
            //更新redis延时任务记录
            cereRedisKeyServcice.updateByKey(StringEnum.SHOP_TOOL_IN.getCode()+"-"+cereShopMarketTool.getToolId(),cereShopMarketTool.getStartTime());
        }
        cereShopMarketToolDAO.updateByPrimaryKeySelective(cereShopMarketTool);
        //新增优惠方案明细数据
        if(!EmptyUtils.isEmpty(param.getDetails())){
            //清空方案明细数据
            cereShopMarketToolDetailService.deleteByToolId(param.getToolId());
            param.getDetails().forEach((detail -> {
                detail.setToolId(cereShopMarketTool.getToolId());
            }));
        }
        //批量插入方案明细数据
        cereShopMarketToolDetailService.insertBatch(param.getDetails());
        //新增日志
        cerePlatformLogService.addLog(user,"营销工具管理","商户端操作","修改营销工具",cereShopMarketTool.getToolId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ToolDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        //清除redis任务
        stringRedisService.delete(StringEnum.SHOP_TOOL_END.getCode()+"-"+param.getToolId());
        stringRedisService.delete(StringEnum.SHOP_TOOL_IN.getCode()+"-"+param.getToolId());
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_TOOL_END.getCode()+"-"+param.getToolId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_TOOL_IN.getCode()+"-"+param.getToolId());
        String time =TimeUtils.yyMMddHHmmss();
        cereShopMarketToolDAO.deleteByPrimaryKey(param.getToolId());
        //清空方案明细数据
        cereShopMarketToolDetailService.deleteByToolId(param.getToolId());
        //新增日志
        cerePlatformLogService.addLog(user,"营销工具管理","商户端操作","修改营销工具",param.getToolId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void end(ToolEndParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //修改营销工具状态为已结束
        CereShopMarketTool cereShopMarketTool=new CereShopMarketTool();
        cereShopMarketTool.setToolId(param.getToolId());
        cereShopMarketTool.setUpdateTime(time);
        cereShopMarketTool.setState(IntegerEnum.TOOL_END.getCode());
        cereShopMarketToolDAO.updateByPrimaryKeySelective(cereShopMarketTool);
        //新增日志
        cerePlatformLogService.addLog(user,"营销工具管理","商户端操作","结束营销工具",cereShopMarketTool.getToolId(),time);
    }

    @Override
    public MarketTool getById(Long toolId) throws CoBusinessException {
        MarketTool marketTool = cereShopMarketToolDAO.getById(toolId);
        if(marketTool!=null){
            //查询优惠方案明细数据
            marketTool.setDetails(cereShopMarketToolDetailService.findByToolId(toolId));
        }
        return marketTool;
    }

    @Override
    public List<MarketTool> getAll(ToolGetAllParam param) throws CoBusinessException {
        List<MarketTool> list=cereShopMarketToolDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.stream()
                    //查询优惠券已使用数量
                    .peek(a-> a.setUse(cereBuyerToolService.findUse(a.getToolId())))
                    //查询优惠券已领取数量
                    .peek(a-> a.setReceive(cereBuyerToolService.findReceive(a.getToolId())));
        }
        return list;
    }
}
