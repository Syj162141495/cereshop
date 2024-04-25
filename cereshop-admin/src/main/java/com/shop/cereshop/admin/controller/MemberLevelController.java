/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.member.MemberLevel;
import com.shop.cereshop.admin.param.member.*;
import com.shop.cereshop.admin.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.admin.service.member.CerePlatformMembershipService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
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
 * 会员等级
 */
@RestController
@RequestMapping("memberlevel")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "MemberLevelController")
@Api(value = "会员模块", tags = "会员模块")
public class MemberLevelController {

    @Autowired
    private CerePlatformMemberLevelService cerePlatformMemberLevelService;

    /**
     * 添加会员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加会员等级")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated MemberLevelSaveParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformMemberLevelService.save(param,user);
        return new Result(user.getUsername(),"添加会员等级", GsonUtil.objectToGson(param));
    }

    /**
     * 编辑会员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "编辑会员等级")
    @NoRepeatWebLog
    public Result update(@RequestBody MemberLevelUpdateParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformMemberLevelService.update(param,user);
        return new Result(user.getUsername(),"编辑会员等级", GsonUtil.objectToGson(param));
    }

    /**
     * 删除会员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除会员等级")
    @NoRepeatWebLog
    public Result delete(@RequestBody MemberLevelgetByIdParam param, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformMemberLevelService.delete(param,user);
        return new Result(user.getUsername(),"删除会员等级", GsonUtil.objectToGson(param));
    }

    /**
     * 会员等级详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "会员等级详情查询")
    public Result<CerePlatformMembership> getById(@RequestBody MemberLevelgetByIdParam param) throws CoBusinessException {
        MemberLevel memberLevel=cerePlatformMemberLevelService.getById(param);
        return new Result(memberLevel);
    }

    /**
     * 会员等级列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "会员等级列表查询")
    public Result<Page<MemberLevel>> getAll(@RequestBody PageParam param) throws CoBusinessException {
        Page page=cerePlatformMemberLevelService.getAll(param);
        return new Result(page);
    }
}
