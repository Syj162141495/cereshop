/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.distribution.DistributionOrder;
import com.shop.cereshop.business.param.distributorOrder.OrderGetAllParam;
import com.shop.cereshop.business.param.distributorOrder.OrderSettlementParam;
import com.shop.cereshop.business.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
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
 * 分销业绩-分销订单
 */
@RestController
@RequestMapping("distributor_order")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "DistributorOrderController")
@Api(value = "分销订单模块", tags = "分销订单模块")
public class DistributorOrderController {

    @Autowired
    private CereDistributionOrderService cereDistributionOrderService;

    /**
     * 分销订单管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "分销订单管理查询")
    public Result<Page<DistributionOrder>> getAll(@RequestBody OrderGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereDistributionOrderService.getAll(param);
        return new Result(page);
    }

    /**
     * 标记结算
     * @param param
     * @return
     */
    @RequestMapping(value = "settlement",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "标记结算")
    @NoRepeatWebLog
    public Result settlement(@RequestBody OrderSettlementParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereDistributionOrderService.settlement(param,user);
        return new Result(user.getUsername(),"标记结算", GsonUtil.objectToGson(param));
    }
}
