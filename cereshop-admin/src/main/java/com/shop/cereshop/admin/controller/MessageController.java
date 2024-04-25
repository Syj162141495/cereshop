/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.message.*;
import com.shop.cereshop.admin.service.message.CerePlatfromMessageService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.message.CerePlatfromMessage;
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
 * 短信账号配置
 */
@RestController
@RequestMapping("message")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "MessageController")
@Api(value = "短信账号配置模块", tags = "短信账号配置模块")
public class MessageController {

    @Autowired
    private CerePlatfromMessageService cerePlatfromMessageService;

    /**
     * 添加短信模板配置
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加短信模板配置")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated MessageSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatfromMessageService.save(param,user);
        return new Result(user.getUsername(),"添加短信模板配置", GsonUtil.objectToGson(param));
    }

    /**
     * 修改短信模板配置
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改短信模板配置")
    @NoRepeatWebLog
    public Result update(@RequestBody MessageUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatfromMessageService.update(param,user);
        return new Result(user.getUsername(),"修改短信模板配置", GsonUtil.objectToGson(param));
    }

    /**
     * 删除短信模板配置
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除短信模板配置")
    @NoRepeatWebLog
    public Result delete(@RequestBody MessageDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatfromMessageService.delete(param,user);
        return new Result(user.getUsername(),"删除短信模板配置", GsonUtil.objectToGson(param));
    }

    /**
     * 短信模板配置修改查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "短信模板配置修改查询")
    public Result<CerePlatfromMessage> getById(@RequestBody MessageGetByIdParam param) throws CoBusinessException{
        CerePlatfromMessage cerePlatfromMessage=cerePlatfromMessageService.getById(param.getMessageId());
        return new Result(cerePlatfromMessage);
    }

    /**
     * 短信模板配置管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "短信模板配置管理查询")
    public Result<Page<CerePlatfromMessage>> getAll(@RequestBody MessageGetAllParam param) throws CoBusinessException{
        Page page=cerePlatfromMessageService.getAll(param);
        return new Result(page);
    }
}
