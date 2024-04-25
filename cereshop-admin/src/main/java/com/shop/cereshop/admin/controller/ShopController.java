/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.shop.ShopGetAll;
import com.shop.cereshop.admin.param.shop.*;
import com.shop.cereshop.admin.service.shop.CerePlatformShopService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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
 * 商家
 */
@RestController
@RequestMapping("shop")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopController")
@Api(value = "商家模块", tags = "商家模块")
public class ShopController {

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    /**
     * 新建商家
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新建商家")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated ShopSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.findByShopName(param.getShopName());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.findByPhone(param.getShopPhone());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.save(param,user);
        return new Result(user.getUsername(),"新建商家", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商家
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改商家")
    @NoRepeatWebLog
    public Result update(@RequestBody ShopUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.checkShopIdByName(param.getShopName(),param.getShopId());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.checkShopIdByPhone(param.getShopPhone(),param.getShopId());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.update(param,user);
        return new Result(user.getUsername(),"修改商家", GsonUtil.objectToGson(param));
    }

    /**
     * 启停用
     * @param param
     * @return
     */
    @RequestMapping(value = "start",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "启停用")
    @NoRepeatWebLog
    public Result start(@RequestBody ShopStartParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformShopService.startOrStop(param,user);
        return new Result(user.getUsername(),"启停用", GsonUtil.objectToGson(param));
    }

    /**
     * 商家编辑查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "商家编辑查询")
    public Result<ShopGetAll> getById(@RequestBody ShopGetByIdParam param) throws CoBusinessException,Exception{
        ShopGetAll shop=cerePlatformShopService.getById(param.getShopId());
        return new Result(shop);
    }

    /**
     * 商家管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "商家管理查询")
    public Result<Page<ShopGetAll>> getAll(@RequestBody ShopGetAllParam param) throws CoBusinessException{
        Page page=cerePlatformShopService.getAll(param);
        return new Result(page);
    }
}
