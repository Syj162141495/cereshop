/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.shop.CereShopExtensionService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopExtension;
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
import java.util.List;

/**
 * 店铺推广
 */
@RestController
@RequestMapping("extension")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopExtensionController")
@Api(value = "店铺推广模块", tags = "店铺推广模块")
public class ShopExtensionController {

    @Autowired
    private CereShopExtensionService cereShopExtensionService;

    /**
     * 添加或修改推广设置
     * @param shopExtension
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加或修改推广设置")
    @NoRepeatWebLog
    public Result save(@RequestBody CereShopExtension shopExtension, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        shopExtension.setShopId(ContextUtil.getShopId());
        cereShopExtensionService.save(shopExtension,user);
        return new Result(user.getUsername(),"添加或修改推广设置", GsonUtil.objectToGson(shopExtension));
    }

    /**
     * 推广设置查询
     * @param shopExtension
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "推广设置查询")
    public Result<List<CereShopExtension>> getAll(@RequestBody ShopParam shopExtension) throws CoBusinessException{
        shopExtension.setShopId(ContextUtil.getShopId());
        List<CereShopExtension> list=cereShopExtensionService.getAll(shopExtension);
        return new Result(list);
    }
}
