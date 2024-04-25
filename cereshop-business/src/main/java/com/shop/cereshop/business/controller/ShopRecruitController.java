/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.param.recruit.ShopRecruitParam;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.shop.CereShopRecruitService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopRecruit;
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
 * 店铺招募信息
 */
@RestController
@RequestMapping("recruit")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopRecruitController")
@Api(value = "店铺招募信息模块", tags = "店铺招募信息模块")
public class ShopRecruitController {

    @Autowired
    private CereShopRecruitService cereShopRecruitService;

    /**
     * 添加招募设置
     * @param recruit
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加招募设置")
    @NoRepeatWebLog
    public Result save(@RequestBody ShopRecruitParam recruit, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        recruit.setShopId(ContextUtil.getShopId());
        cereShopRecruitService.save(recruit,user);
        return new Result(user.getUsername(),"添加招募设置", GsonUtil.objectToGson(recruit));
    }

    /**
     * 修改招募设置
     * @param recruit
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改招募设置")
    @NoRepeatWebLog
    public Result update(@RequestBody ShopRecruitParam recruit, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        recruit.setShopId(ContextUtil.getShopId());
        cereShopRecruitService.update(recruit,user);
        return new Result(user.getUsername(),"修改招募设置", GsonUtil.objectToGson(recruit));
    }

    /**
     * 招募设置查询
     * @param recruit
     * @return
     */
    @RequestMapping(value = "getByShopId",method = RequestMethod.POST)
    @ApiOperation(value = "招募设置查询")
    public Result<CereShopRecruit> getByShopId(@RequestBody ShopParam recruit) throws CoBusinessException{
        recruit.setShopId(ContextUtil.getShopId());
        ShopRecruitParam cereShopRecruit=cereShopRecruitService.getByShopId(recruit.getShopId());
        return new Result(cereShopRecruit);
    }
}
