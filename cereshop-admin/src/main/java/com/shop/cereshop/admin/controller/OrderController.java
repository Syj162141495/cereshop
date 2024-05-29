/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.page.order.ShopOrder;
import com.shop.cereshop.admin.param.order.OrderGetAllParam;
import com.shop.cereshop.admin.param.order.OrderGetByIdParam;
import com.shop.cereshop.admin.service.order.CereShopOrderService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 */
@RestController
@RequestMapping("order")
@Slf4j(topic = "OrderController")
@Api(value = "订单管理", tags = "订单管理")
public class OrderController {

    @Autowired
    private CereShopOrderService cereShopOrderService;

    /**
     * 订单管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "订单管理查询")
    public Result<Page<ShopOrder>> getAll(@RequestBody OrderGetAllParam param) throws CoBusinessException {
        Page page =cereShopOrderService.getAll(param);
        return new Result(page);
    }

    /**
     * 订单详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "订单详情查询")
    public Result<ShopOrder> getById(@RequestBody OrderGetByIdParam param) throws CoBusinessException{
        ShopOrder shopOrder=cereShopOrderService.getById(param.getOrderId());
        return new Result(shopOrder);
    }
}
