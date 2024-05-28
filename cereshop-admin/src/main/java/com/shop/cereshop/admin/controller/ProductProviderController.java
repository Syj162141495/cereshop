/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;


import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.product.CustomerClassify;
import com.shop.cereshop.admin.page.product.ProductProviderClassify;
import com.shop.cereshop.admin.param.product.ClassDeleteParam;
import com.shop.cereshop.admin.param.product.ClassGetByIdParam;
import com.shop.cereshop.admin.param.product.ProductGetClassifyParam;
import com.shop.cereshop.admin.param.product.ProductProviderClassifyParam;
import com.shop.cereshop.admin.service.product.CereProductProviderClassifyService;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.domain.product.Classify;
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
import java.util.List;

/**
 * 商品类别
 */
@RestController
@RequestMapping("providerClassify")
@Slf4j(topic = "CustomerClassifyController")
@Api(value = "服务商类别模块", tags = "服务商类别模块")
public class ProductProviderController {

    @Autowired
    private CereProductProviderClassifyService cereProductProviderClassifyService;

    /**
     * 添加商品类别
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加服务商类别")
    @NoRepeatWebLog
    public Result add(@RequestBody @Validated ProductProviderClassifyParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductProviderClassifyService.add(param, user);
        return new Result(user.getUsername(), "添加商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商品类别
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改服务商类别")
    @NoRepeatWebLog
    public Result update(@RequestBody ProductProviderClassifyParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        System.out.println(param);
        cereProductProviderClassifyService.update(param, user);
        return new Result(user.getUsername(), "修改商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 删除商品类别
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除服务商类别")
    @NoRepeatWebLog
    public Result delete(@RequestBody ClassDeleteParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        System.out.println(param);
        cereProductProviderClassifyService.delete(param, user);
        return new Result(user.getUsername(), "删除商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 商品类别按id查询
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    @ApiOperation(value = "服务商类别编辑查询")
    public Result<ProductProviderClassify> getById(@RequestBody ClassGetByIdParam param) throws CoBusinessException {
        ProductProviderClassify classify = cereProductProviderClassifyService.getById(param.getClassifyId());
        return new Result(classify);
    }

    /**
     * 商品类别按pid查询
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "getByPid", method = RequestMethod.POST)
    @ApiOperation(value = "服务商类别编辑查询")
    public Result<List<ProductProviderClassify>> getByPid(@RequestBody ClassGetByIdParam param) throws CoBusinessException {
        List<ProductProviderClassify> classifys = cereProductProviderClassifyService.getByPid(param.getClassifyId());
        return new Result(classifys);
    }
}
