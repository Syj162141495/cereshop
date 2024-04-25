/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.sensitive.SensitiveSaveParam;
import com.shop.cereshop.admin.param.sensitive.SensitiveUpdateParam;
import com.shop.cereshop.admin.service.sensitive.CerePlatformSensitiveService;
import com.shop.cereshop.commons.domain.sensitive.CerePlatformSensitive;
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
 * 敏感词管理
 */
@RestController
@RequestMapping("sensitive")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "SensitiveController")
@Api(value = "敏感词管理模块", tags = "敏感词管理模块")
public class SensitiveController {

    @Autowired
    private CerePlatformSensitiveService cerePlatformSensitiveService;

    /**
     * 添加敏感词设置
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加敏感词设置")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated SensitiveSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSensitiveService.save(param,user);
        return new Result(user.getUsername(),"添加敏感词设置", GsonUtil.objectToGson(param));
    }

    /**
     * 修改敏感词设置
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改敏感词设置")
    @NoRepeatWebLog
    public Result update(@RequestBody SensitiveUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSensitiveService.update(param,user);
        return new Result(user.getUsername(),"修改敏感词设置", GsonUtil.objectToGson(param));
    }

    /**
     * 敏感词查询
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.POST)
    @ApiOperation(value = "敏感词查询")
    public Result<List<CerePlatformSensitive>> get() throws CoBusinessException{
        List<CerePlatformSensitive> sensitive=cerePlatformSensitiveService.get();
        return new Result(sensitive);
    }
}
