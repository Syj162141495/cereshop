/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.notice.NoticeGetAllParam;
import com.shop.cereshop.admin.param.notice.NoticeIdParam;
import com.shop.cereshop.admin.param.notice.NoticeParam;
import com.shop.cereshop.admin.service.notice.CereNoticeService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.notice.CereNotice;
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
 * 消息中心
 */
@RestController
@RequestMapping("notice")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "NoticeController")
@Api(value = "消息中心", tags = "消息中心")
public class NoticeController {

    @Autowired
    private CereNoticeService cereNoticeService;

    /**
     * 消息推送
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "消息推送")
    @NoRepeatWebLog
    public Result save(@RequestBody NoticeParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereNoticeService.save(param,user);
        return new Result(user.getUsername(),"消息推送", GsonUtil.objectToGson(param));
    }

    /**
     * 删除消息
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除消息")
    @NoRepeatWebLog
    public Result delete(@RequestBody NoticeIdParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereNoticeService.delete(param,user);
        return new Result(user.getUsername(),"删除消息", GsonUtil.objectToGson(param));
    }

    /**
     * 消息详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "消息详情查询")
    public Result<CereNotice> getById(@RequestBody NoticeIdParam param) throws CoBusinessException{
        CereNotice cereNotice=cereNoticeService.getById(param.getNoticeId());
        return new Result(cereNotice);
    }

    /**
     * 历史消息查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "历史消息查询")
    public Result<Page<CereNotice>> getAll(@RequestBody NoticeGetAllParam param) throws CoBusinessException{
        Page page =cereNoticeService.getAll(param);
        return new Result(page);
    }
}
