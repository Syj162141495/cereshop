/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.tool.ShopCoupon;
import com.shop.cereshop.business.page.tool.ShopCouponData;
import com.shop.cereshop.business.page.tool.ShopCouponDetail;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.service.tool.CereShopCouponService;
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
 * 店铺优惠券管理
 */
@RestController
@RequestMapping("coupon")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopCouponController")
@Api(value = "优惠券管理模块", tags = "优惠券管理模块")
public class ShopCouponController {

    @Autowired
    private CereShopCouponService cereShopCouponService;

    /**
     * 新增满减券/折扣券
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增满减券/折扣券")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated ShopCouponSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopCouponService.save(param,user);
        return new Result(user.getUsername(),"新增满减券/折扣券", GsonUtil.objectToGson(param));
    }

    /**
     * 修改满减券/折扣券
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改满减券/折扣券")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated ShopCouponUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopCouponService.update(param,user);
        return new Result(user.getUsername(),"修改满减券/折扣券", GsonUtil.objectToGson(param));
    }

    /**
     * 删除满减券/折扣券
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除满减券/折扣券")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShopCouponGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopCouponService.delete(param.getShopCouponId(),user);
        return new Result(user.getUsername(),"删除满减券/折扣券", GsonUtil.objectToGson(param));
    }

    /**
     * 停止满减券/折扣券
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止满减券/折扣券")
    @NoRepeatWebLog
    public Result stop(@RequestBody ShopCouponGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopCouponService.stop(param.getShopCouponId(),user);
        return new Result(user.getUsername(),"停止满减券/折扣券", GsonUtil.objectToGson(param));
    }

    /**
     * 满减券/折扣券详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "满减券/折扣券详情查询")
    public Result<ShopCouponDetail> getById(@RequestBody ShopCouponGetByIdParam param) throws CoBusinessException{
        ShopCouponDetail detail=cereShopCouponService.getById(param.getShopCouponId());
        return new Result(detail);
    }

    /**
     * 数据效果查询
     * @return
     */
    @RequestMapping(value = "getData",method = RequestMethod.POST)
    @ApiOperation(value = "数据效果查询")
    public Result<ShopCouponData> getData(@RequestBody ShopCouponGetByIdParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        ShopCouponData detail=cereShopCouponService.getData(param.getShopCouponId(),user.getShopId());
        return new Result(detail);
    }

    /**
     * 满减券/折扣券管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "满减券/折扣券管理查询")
    public Result<Page<ShopCoupon>> getAll(@RequestBody ShopCouponGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopCouponService.getAll(param);
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
        Page page=cereShopCouponService.getProducts(param);
        return new Result(page);
    }
}
