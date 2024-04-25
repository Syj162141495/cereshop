/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.dict.DictSaveParam;
import com.shop.cereshop.admin.param.member.MembershipGetByIdParam;
import com.shop.cereshop.admin.param.member.MembershipSaveParam;
import com.shop.cereshop.admin.param.member.MembershipUpdateParam;
import com.shop.cereshop.admin.service.member.CerePlatformMembershipService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.domain.member.CerePlatformMembership;
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
 * 会员权益
 */
@RestController
@RequestMapping("membership")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "MemberShipController")
@Api(value = "会员模块", tags = "会员模块")
public class MemberShipController {

    @Autowired
    private CerePlatformMembershipService cerePlatformMembershipService;

    /**
     * 添加会员权益
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加会员权益")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated MembershipSaveParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformMembershipService.save(param,user);
        return new Result(user.getUsername(),"添加会员权益", GsonUtil.objectToGson(param));
    }

    /**
     * 编辑会员权益
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "编辑会员权益")
    @NoRepeatWebLog
    public Result update(@RequestBody MembershipUpdateParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformMembershipService.update(param,user);
        return new Result(user.getUsername(),"编辑会员权益", GsonUtil.objectToGson(param));
    }

    /**
     * 删除会员权益
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除会员权益")
    @NoRepeatWebLog
    public Result delete(@RequestBody MembershipGetByIdParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformMembershipService.delete(param,user);
        return new Result(user.getUsername(),"删除会员权益", GsonUtil.objectToGson(param));
    }

    /**
     * 会员权益详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "会员权益详情查询")
    public Result<CerePlatformMembership> getById(@RequestBody MembershipGetByIdParam param) throws CoBusinessException {
        CerePlatformMembership membership=cerePlatformMembershipService.getById(param);
        return new Result(membership);
    }

    /**
     * 会员权益列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "会员权益列表查询")
    public Result<Page<CerePlatformMembership>> getAll(@RequestBody PageParam param) throws CoBusinessException {
        Page page=cerePlatformMembershipService.getAll(param);
        return new Result(page);
    }
}
