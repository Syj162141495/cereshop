/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.compose.ShopCompose;
import com.shop.cereshop.business.page.compose.ShopComposeDetail;
import com.shop.cereshop.business.page.price.ShopPrice;
import com.shop.cereshop.business.page.price.ShopPriceDetail;
import com.shop.cereshop.business.param.compose.*;
import com.shop.cereshop.business.param.price.*;
import com.shop.cereshop.business.service.compose.CereShopComposeService;
import com.shop.cereshop.business.service.price.CereShopPriceService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 定价捆绑销售
 */
@RestController
@RequestMapping("price")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "PriceController")
@Api(value = "定价捆绑销售", tags = "定价捆绑销售")
public class PriceController {

    @Autowired
    private CereShopPriceService cereShopPriceService;

    /**
     * 添加定价捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加定价捆绑")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated PriceSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopPriceService.save(param,user);
        return new Result(user.getUsername(),"添加定价捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 编辑定价捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "编辑定价捆绑")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated PriceUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopPriceService.update(param,user);
        return new Result(user.getUsername(),"编辑定价捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 删除定价捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除定价捆绑")
    @NoRepeatWebLog
    public Result delete(@RequestBody PriceGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopPriceService.delete(param,user);
        return new Result(user.getUsername(),"删除定价捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 启停用定价捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "start",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "启停用定价捆绑")
    @NoRepeatWebLog
    public Result start(@RequestBody PriceStartParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopPriceService.start(param,user);
        return new Result(user.getUsername(),"启停用定价捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 定价捆绑详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "定价捆绑详情查询")
    public Result<ShopPriceDetail> getById(@RequestBody PriceGetByIdParam param) throws CoBusinessException{
        ShopPriceDetail detail=cereShopPriceService.getById(param.getPriceId());
        return new Result(detail);
    }

    /**
     * 定价捆绑列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "定价捆绑列表查询")
    public Result<Page<ShopPrice>> getAll(@RequestBody PriceGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopPriceService.getAll(param);
        return new Result(page);
    }
}
