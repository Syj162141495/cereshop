/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.polite.Polite;
import com.shop.cereshop.admin.page.polite.PoliteActivity;
import com.shop.cereshop.admin.page.polite.PoliteData;
import com.shop.cereshop.admin.page.polite.PoliteDetail;
import com.shop.cereshop.admin.page.seckill.SeckillData;
import com.shop.cereshop.admin.param.polite.PoliteGetAllParam;
import com.shop.cereshop.admin.param.polite.PoliteGetByIdParam;
import com.shop.cereshop.admin.param.polite.PoliteSaveParam;
import com.shop.cereshop.admin.param.polite.PoliteUpdateParam;
import com.shop.cereshop.admin.param.seckill.SeckillGetByIdParam;
import com.shop.cereshop.admin.param.seckill.SeckillSaveParam;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
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
 * 平台支付有礼活动管理
 */
@RestController
@RequestMapping("polite")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "SeckillController")
@Api(value = "平台支付有礼活动管理模块", tags = "平台支付有礼活动管理模块")
public class PoliteController {

    @Autowired
    private CerePlatformPoliteService cerePlatformPoliteService;

    /**
     * 新增平台支付有礼活动
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增平台支付有礼活动")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated PoliteSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPoliteService.save(param,user);
        return new Result(user.getUsername(),"新增平台支付有礼活动", GsonUtil.objectToGson(param));
    }

    /**
     * 编辑平台支付有礼活动
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "编辑平台支付有礼活动")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated PoliteUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPoliteService.update(param,user);
        return new Result(user.getUsername(),"编辑平台支付有礼活动", GsonUtil.objectToGson(param));
    }

    /**
     * 删除平台支付有礼活动
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除平台支付有礼活动")
    @NoRepeatWebLog
    public Result delete(@RequestBody PoliteGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPoliteService.delete(param,user);
        return new Result(user.getUsername(),"删除平台支付有礼活动", GsonUtil.objectToGson(param));
    }

    /**
     * 停止平台支付有礼活动
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止平台支付有礼活动")
    @NoRepeatWebLog
    public Result stop(@RequestBody PoliteGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPoliteService.stop(param,user);
        return new Result(user.getUsername(),"停止平台支付有礼活动", GsonUtil.objectToGson(param));
    }

    /**
     * 平台支付有礼详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "平台支付有礼详情查询")
    public Result<PoliteDetail> getById(@RequestBody PoliteGetByIdParam param) throws CoBusinessException{
        PoliteDetail detail=cerePlatformPoliteService.getById(param.getPoliteId());
        return new Result(detail);
    }

    /**
     * 数据效果查询
     * @return
     */
    @RequestMapping(value = "getData",method = RequestMethod.POST)
    @ApiOperation(value = "数据效果查询")
    public Result<PoliteData> getData(@RequestBody PoliteGetByIdParam param) throws CoBusinessException{
        PoliteData detail=cerePlatformPoliteService.getData(param);
        return new Result(detail);
    }

    /**
     * 平台支付有礼管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "平台支付有礼管理查询")
    public Result<Page<Polite>> getAll(@RequestBody PoliteGetAllParam param) throws CoBusinessException{
        Page page=cerePlatformPoliteService.getAll(param);
        return new Result(page);
    }

    /**
     * 选择优惠券查询
     * @return
     */
    @RequestMapping(value = "getAllActivity",method = RequestMethod.POST)
    @ApiOperation(value = "选择优惠券查询")
    public Result<Page<PoliteActivity>> getAllActivity(@RequestBody PageParam param) throws CoBusinessException{
        Page page=cerePlatformPoliteService.getAllActivity(param);
        return new Result(page);
    }
}
