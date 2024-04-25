/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.page.distribution.Achievement;
import com.shop.cereshop.business.page.distribution.AchievementDetai;
import com.shop.cereshop.business.param.distributor.*;
import com.shop.cereshop.business.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 分销员业绩管理
 */
@RestController
@RequestMapping("achievement")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "AchievementController")
@Api(value = "分销员业绩管理模块", tags = "分销员业绩管理模块")
public class AchievementController {

    @Autowired
    private CereDistributionOrderService cereDistributionOrderService;

    /**
     * 分销员业绩管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAllAchievement",method = RequestMethod.POST)
    @ApiOperation(value = "分销员业绩管理查询")
    public Result<Page<Achievement>> getAllAchievement(@RequestBody DistributorGetAllAchievementParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereDistributionOrderService.getAllAchievement(param);
        return new Result(page);
    }

    /**
     * 订单数详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getOrderDetail",method = RequestMethod.POST)
    @ApiOperation(value = "订单数详情查询")
    public Result<AchievementDetai> getOrderDetail(@RequestBody DistributorGetOrderDetailParam param) throws CoBusinessException{
        AchievementDetai detai=cereDistributionOrderService.getOrderDetail(param.getDistributorId());
        return new Result(detai);
    }

    /**
     * 成交金额详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getDealMoneyDetail",method = RequestMethod.POST)
    @ApiOperation(value = "成交金额详情查询")
    public Result<AchievementDetai> getDealMoneyDetail(@RequestBody DistributorGetDealMoneyParam param) throws CoBusinessException{
        AchievementDetai detai=cereDistributionOrderService.getDealMoneyDetail(param.getDistributorId());
        return new Result(detai);
    }

    /**
     * 总佣金详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getCommissionMoneyDetail",method = RequestMethod.POST)
    @ApiOperation(value = "总佣金详情查询")
    public Result<AchievementDetai> getCommissionMoneyDetail(@RequestBody DistributorGetCommissionParam param) throws CoBusinessException{
        AchievementDetai detai=cereDistributionOrderService.getCommissionMoneyDetail(param.getDistributorId());
        return new Result(detai);
    }

    /**
     * 未结算佣金详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getNotCommissionMoneyDetail",method = RequestMethod.POST)
    @ApiOperation(value = "未结算佣金详情查询")
    public Result<AchievementDetai> getNotCommissionMoneyDetail(@RequestBody DistributorGetNotCommissionParam param) throws CoBusinessException{
        AchievementDetai detai=cereDistributionOrderService.getNotCommissionMoneyDetail(param.getDistributorId());
        return new Result(detai);
    }
}
