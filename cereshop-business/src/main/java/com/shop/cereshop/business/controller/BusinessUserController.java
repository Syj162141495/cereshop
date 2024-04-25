/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.shop.PlatformBusiness;
import com.shop.cereshop.business.page.user.Business;
import com.shop.cereshop.business.param.user.BusinessDeleteUser;
import com.shop.cereshop.business.param.user.BusinessGetAllUser;
import com.shop.cereshop.business.param.user.BusinessSaveUser;
import com.shop.cereshop.business.param.user.BusinessUpdateUser;
import com.shop.cereshop.business.service.business.CerePlatformBusinessService;
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
 * 用户管理
 */
@RestController
@RequestMapping("user")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BusinessUserController")
@Api(value = "用户管理", tags = "用户管理")
public class BusinessUserController {

    @Autowired
    private CerePlatformBusinessService cerePlatformBusinessService;

    /**
     * 添加用户
     * @param business
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加用户")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated BusinessSaveUser business, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        business.setShopId(user.getShopId());
        cerePlatformBusinessService.save(business,user);
        return new Result(user.getUsername(),"添加用户", GsonUtil.objectToGson(business));
    }

    /**
     * 修改用户
     * @param business
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改用户")
    @NoRepeatWebLog
    public Result update(@RequestBody BusinessUpdateUser business, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cerePlatformBusinessService.update(business,user);
        return new Result(user.getUsername(),"修改用户", GsonUtil.objectToGson(business));
    }

    /**
     * 删除用户
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除用户")
    @NoRepeatWebLog
    public Result delete(@RequestBody BusinessDeleteUser param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cerePlatformBusinessService.delete(param,user);
        return new Result(user.getUsername(),"删除用户", GsonUtil.objectToGson(param));
    }

    /**
     * 修改用户查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户查询")
    public Result<PlatformBusiness> getById(@RequestBody BusinessDeleteUser param) throws CoBusinessException,Exception{
        Business business=cerePlatformBusinessService.getById(param.getBusinessUserId());
        return new Result(business);
    }

    /**
     * 用户管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "用户管理查询")
    @NoRepeatSubmit
    public Result<Page<CerePlatformBusiness>> getAll(@RequestBody BusinessGetAllUser param,HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page=cerePlatformBusinessService.getAll(param);
        return new Result(page);
    }
}
