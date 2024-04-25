/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.user.PlatformUser;
import com.shop.cereshop.admin.param.user.*;
import com.shop.cereshop.admin.service.user.CerePlatformUserService;
import com.shop.cereshop.commons.domain.common.Page;
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
 * 用户
 */
@RestController
@RequestMapping("platform_user")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "PlatformUserController")
@Api(value = "用户模块", tags = "用户模块")
public class PlatformUserController {

    @Autowired
    private CerePlatformUserService cerePlatformUserService;

    /**
     * 添加账户
     *
     * @param userParam
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加账户")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated UserSaveParam userParam, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.save(userParam, user);
        return new Result(user.getUsername(), "添加账户", GsonUtil.objectToGson(userParam));
    }

    /**
     * 修改账户
     *
     * @param userParam
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改账户")
    @NoRepeatWebLog
    public Result update(@RequestBody UserUpdateParam userParam, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.update(userParam, user);
        return new Result(user.getUsername(), "修改账户", GsonUtil.objectToGson(userParam));
    }

    /**
     * 删除账户
     *
     * @param userParam
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除账户")
    @NoRepeatWebLog
    public Result delete(@RequestBody UserDeleteParam userParam, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.delete(userParam, user);
        return new Result(user.getUsername(), "删除账户", GsonUtil.objectToGson(userParam));
    }

    /**
     * 账户编辑查询
     *
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    @ApiOperation(value = "账户编辑查询")
    public Result<PlatformUser> getById(@RequestBody UserGetByIdParam userParam) throws CoBusinessException, Exception {
        PlatformUser cerePlatformUser = cerePlatformUserService.getById(userParam.getPlatformUserId());
        return new Result(cerePlatformUser);
    }

    /**
     * 账户管理查询
     *
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    @ApiOperation(value = "账户管理查询")
    public Result<Page<PlatformUser>> getAll(@RequestBody UserGetAllParam userParam) throws CoBusinessException {
        Page page = cerePlatformUserService.getAll(userParam);
        return new Result(page);
    }

    /**
     * 更新密码
     *
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ApiOperation(value = "更新密码")
    public Result updatePassword(@RequestBody UserForgetPasswordParam passwordParam, HttpServletRequest request) throws CoBusinessException {
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.updatePassword(passwordParam, user);
        return new Result();
    }
}
