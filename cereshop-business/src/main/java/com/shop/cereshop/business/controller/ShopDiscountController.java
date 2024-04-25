/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.tool.ShopDiscount;
import com.shop.cereshop.business.page.tool.ShopDiscountData;
import com.shop.cereshop.business.page.tool.ShopDiscountDetail;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.service.tool.CereShopDiscountService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EmptyUtils;
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
 * 店铺限时折扣活动管理
 */
@RestController
@RequestMapping("discount")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopDiscountController")
@Api(value = "店铺限时折扣活动管理模块", tags = "店铺限时折扣活动管理模块")
public class ShopDiscountController {

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    /**
     * 新增限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增限时折扣活动")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated ShopDiscountSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        if(EmptyUtils.isEmpty(param.getDetails())){
            throw new CoBusinessException(CoReturnFormat.HAVE_ONE_TOOL_PRODUCT);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopDiscountService.save(param,user);
        return new Result(user.getUsername(),"新增限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 修改限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改限时折扣活动")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated ShopDiscountUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        if(EmptyUtils.isEmpty(param.getDetails())){
            throw new CoBusinessException(CoReturnFormat.HAVE_ONE_TOOL_PRODUCT);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopDiscountService.update(param,user);
        return new Result(user.getUsername(),"修改限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 删除限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除限时折扣活动")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShopDiscountGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopDiscountService.delete(param.getShopDiscountId(),user);
        return new Result(user.getUsername(),"删除限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 停止限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止限时折扣活动")
    @NoRepeatWebLog
    public Result stop(@RequestBody ShopDiscountGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopDiscountService.stop(param.getShopDiscountId(),user);
        return new Result(user.getUsername(),"停止限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 限时折扣活动详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "限时折扣活动详情查询")
    public Result<ShopDiscountDetail> getById(@RequestBody ShopDiscountGetByIdParam param) throws CoBusinessException{
        ShopDiscountDetail detail=cereShopDiscountService.getById(param.getShopDiscountId());
        return new Result(detail);
    }

    /**
     * 数据效果查询
     * @return
     */
    @RequestMapping(value = "getData",method = RequestMethod.POST)
    @ApiOperation(value = "数据效果查询")
    public Result<ShopDiscountData> getData(@RequestBody ShopDiscountGetByIdParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        ShopDiscountData detail=cereShopDiscountService.getData(param,user.getShopId());
        return new Result(detail);
    }

    /**
     * 限时折扣活动管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "限时折扣活动管理查询")
    public Result<Page<ShopDiscount>> getAll(@RequestBody ShopDiscountGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopDiscountService.getAll(param);
        return new Result(page);
    }

    /**
     * 选择商品查询
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "选择商品查询")
    public Result<Page<ToolProduct>> getProducts(@RequestBody ToolProductParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page=cereShopDiscountService.getProducts(param);
        return new Result(page);
    }
}
