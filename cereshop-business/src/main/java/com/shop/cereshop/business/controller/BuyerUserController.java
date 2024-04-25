/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.buyer.BuyerUserDetail;
import com.shop.cereshop.business.param.buyer.*;
import com.shop.cereshop.business.service.buyer.CereBuyerUserService;
import com.shop.cereshop.business.service.label.CereBuyerShopLabelService;
import com.shop.cereshop.business.service.label.CereShopUserLabelService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.CereShopUserLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 客户管理
 */
@RestController
@RequestMapping("buyer")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BuyerUserController")
@Api(value = "客户管理模块", tags = "客户管理模块")
public class BuyerUserController {

    @Autowired
    private CereBuyerUserService cereBuyerUserService;

    @Autowired
    private CereBuyerShopLabelService cereBuyerShopLabelService;

    @Autowired
    private CereShopUserLabelService cereShopUserLabelService;

    /**
     * 客户列表
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "客户列表")
    public Result<Page<BuyerUser>> getAll(@RequestBody BuyerUserGetAllParam param,HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page=cereBuyerUserService.getAll(param);
        return new Result(page);
    }

    /**
     * 商家标签列表
     * @return
     */
    @RequestMapping(value = "getLabels",method = RequestMethod.POST)
    @ApiOperation(value = "商家标签列表")
    public Result<List<CereShopUserLabel>> getLabels(HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        List<CereShopUserLabel> list=cereShopUserLabelService.getLabels(user.getShopId());
        return new Result(list);
    }

    /**
     * 加标签
     * @param group
     * @return
     */
    @RequestMapping(value = "saveLabel",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "加标签")
    @NoRepeatWebLog
    public Result saveLabel(@RequestBody BuyerUserSaveParam group, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereBuyerShopLabelService.saveLabel(group,user);
        return new Result(user.getUsername(),"加标签", GsonUtil.objectToGson(group));
    }

    /**
     * 添加客户
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加客户")
    @NoRepeatWebLog
    public Result save(@RequestBody UserSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereBuyerUserService.save(param,user);
        return new Result(user.getUsername(),"添加客户", GsonUtil.objectToGson(param));
    }

    /**
     * 修改客户
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改客户")
    @NoRepeatWebLog
    public Result update(@RequestBody UserUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereBuyerUserService.update(param,user);
        return new Result(user.getUsername(),"修改客户", GsonUtil.objectToGson(param));
    }

    /**
     * 客户详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "客户详情查询")
    public Result<BuyerUserDetail> getById(@RequestBody BuyerUserGetByIdParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        Long shopId = null;
        if (user != null) {
            shopId = user.getShopId();
        }
        BuyerUserDetail detail=cereBuyerUserService.getById(shopId, param.getBuyerUserId());
        return new Result(detail);
    }

    @GetMapping("initBusinessBuyerUser")
    @ApiOperation("初始化成为商家客户的时间")
    public Result<Boolean> initBusinessBuyerUser() {
        cereBuyerUserService.initBusinessBuyerUser();
        return new Result(true, CoReturnFormat.SUCCESS);
    }
}
