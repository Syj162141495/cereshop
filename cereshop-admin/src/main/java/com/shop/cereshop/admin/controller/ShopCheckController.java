/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.shopcheck.CheckDeleteParam;
import com.shop.cereshop.admin.param.shopcheck.CheckGetAllParam;
import com.shop.cereshop.admin.param.shopcheck.CheckGetByIdParam;
import com.shop.cereshop.admin.param.shopcheck.CheckHandleParam;
import com.shop.cereshop.admin.service.shop.CereShopCheckService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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
 * 入驻申请
 */
@RestController
@RequestMapping("check")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopCheckController")
@Api(value = "入驻申请模块", tags = "入驻申请模块")
public class ShopCheckController {

    @Autowired
    private CereShopCheckService cereShopCheckService;

    /**
     * 入驻申请管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "入驻申请管理查询")
    public Result<Page<Shop>> getAll(@RequestBody CheckGetAllParam param) throws CoBusinessException{
        Page page=cereShopCheckService.getAll(param);
        return new Result(page);
    }

    /**
     * 查看
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "查看")
    public Result<Shop> getById(@RequestBody CheckGetByIdParam param) throws CoBusinessException{
        Shop shop=cereShopCheckService.getById(param.getShopId());
        return new Result(shop);
    }

    /**
     * 处理入驻申请
     * @param param
     * @return
     */
    @RequestMapping(value = "handle",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "处理入驻申请")
    @NoRepeatWebLog
    public Result handle(@RequestBody CheckHandleParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCheckService.handle(param,user);
        return new Result(user.getUsername(),"处理入驻申请", GsonUtil.objectToGson(param));
    }

    /**
     * 删除入驻审核记录
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除")
    @NoRepeatWebLog
    public Result delete(@RequestBody CheckDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCheckService.delete(param,user);
        return new Result(user.getUsername(),"删除入驻审核记录", GsonUtil.objectToGson(param));
    }
}
