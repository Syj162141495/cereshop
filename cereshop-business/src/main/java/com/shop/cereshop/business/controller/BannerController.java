/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.shop.ShopBanner;
import com.shop.cereshop.business.param.banner.BannerGetByIdParam;
import com.shop.cereshop.business.param.banner.BannerSaveParam;
import com.shop.cereshop.business.param.banner.BannerUpdateParam;
import com.shop.cereshop.business.service.shop.CereShopBannerService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
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
 * 店铺banner
 */
@RestController
@RequestMapping("banner")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BannerController")
@Api(value = "店铺banner模块", tags = "店铺banner模块")
public class BannerController {

    @Autowired
    private CereShopBannerService cereShopBannerService;

    /**
     * 添加banner
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加banner")
    @NoRepeatWebLog
    public Result save(@RequestBody BannerSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBannerService.save(param,user);
        return new Result(user.getUsername(),"添加banner", GsonUtil.objectToGson(param));
    }

    /**
     * 修改banner
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改banner")
    public Result update(@RequestBody BannerUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBannerService.update(param,user);
        return new Result(user.getUsername(),"修改banner", GsonUtil.objectToGson(param));
    }

    /**
     * banner编辑查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "banner编辑查询")
    public Result<ShopBanner> getById(@RequestBody BannerGetByIdParam param) throws CoBusinessException{
        ShopBanner shopBanner=cereShopBannerService.getById(param.getBannerId());
        return new Result(shopBanner);
    }
}
