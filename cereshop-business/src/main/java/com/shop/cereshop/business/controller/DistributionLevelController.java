/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.distribution.DistributoLevel;
import com.shop.cereshop.business.param.level.*;
import com.shop.cereshop.business.service.distributor.CereShopDistributionLevelService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
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
 * 分销方案（分销员等级）
 */
@RestController
@RequestMapping("level")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "DistributionLevelController")
@Api(value = "分销方案（分销员等级）模块", tags = "分销方案（分销员等级）模块")
public class DistributionLevelController {

    @Autowired
    private CereShopDistributionLevelService cereShopDistributionLevelService;

    /**
     * 添加分销员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加分销员等级")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated LevelSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopDistributionLevelService.save(param,user);
        return new Result(user.getUsername(),"添加分销员等级", GsonUtil.objectToGson(param));
    }

    /**
     * 修改分销员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改分销员等级")
    @NoRepeatWebLog
    public Result update(@RequestBody LevelUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopDistributionLevelService.update(param,user);
        return new Result(user.getUsername(),"修改分销员等级", GsonUtil.objectToGson(param));
    }

    /**
     * 删除分销员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除分销员等级")
    @NoRepeatWebLog
    public Result delete(@RequestBody LevelDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopDistributionLevelService.delete(param,user);
        return new Result(user.getUsername(),"删除分销员等级", GsonUtil.objectToGson(param));
    }

    /**
     * 分销员等级修改查询
     * @param level
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "分销员等级修改查询")
    public Result<CereShopDistributionLevel> getById(@RequestBody LevelGetByIdParam level) throws CoBusinessException{
        DistributoLevel distributoLevel=cereShopDistributionLevelService.getById(level.getDistributorLevelId());
        return new Result(distributoLevel);
    }

    /**
     * 分销员等级管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "分销员等级管理查询")
    public Result<List<CereShopDistributionLevel>> getAll(@RequestBody LevelGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<CereShopDistributionLevel> list=cereShopDistributionLevelService.getAll(param.getShopId());
        return new Result(list);
    }

    /**
     * 开启/关闭自购分佣
     * @param param
     * @return
     */
    @RequestMapping(value = "updateSelf",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "开启/关闭自购分佣")
    @NoRepeatWebLog
    public Result updateSelf(@RequestBody LevelStateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopDistributionLevelService.updateSelf(param,user);
        return new Result(user.getUsername(),"开启/关闭自购分佣", GsonUtil.objectToGson(param));
    }
}
