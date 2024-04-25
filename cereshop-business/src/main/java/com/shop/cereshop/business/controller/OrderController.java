/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.order.ShopOrder;
import com.shop.cereshop.business.param.order.OrderDileveryParam;
import com.shop.cereshop.business.param.order.OrderGetAllParam;
import com.shop.cereshop.business.param.order.OrderGetByIdParam;
import com.shop.cereshop.business.service.order.CereOrderDileverService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
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
 * 订单
 */
@RestController
@RequestMapping("order")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "OrderController")
@Api(value = "订单模块", tags = "订单模块")
public class OrderController {

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Autowired
    private CereOrderDileverService cereOrderDileverService;

    /**
     * 订单管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "订单管理查询")
    public Result<Page<ShopOrder>> getAll(@RequestBody OrderGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopOrderService.getAll(param);
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

    /**
     * 发货
     * @param param
     * @return
     */
    @RequestMapping(value = "dilevery",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "发货")
    @NoRepeatWebLog
    public Result dilevery(@RequestBody OrderDileveryParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderDileverService.dilevery(param,user);
        return new Result(user.getUsername(),"发货", GsonUtil.objectToGson(param));
    }
}
