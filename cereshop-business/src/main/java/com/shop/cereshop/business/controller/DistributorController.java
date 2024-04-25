/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.distribution.ShopDistributor;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.param.shopDistributor.*;
import com.shop.cereshop.business.service.distributor.CereShopDistributionLevelService;
import com.shop.cereshop.business.service.distributor.CereShopDistributorService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
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
 * 分销员
 */
@RestController
@RequestMapping("distributor")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "DistributorController")
@Api(value = "分销员模块", tags = "分销员模块")
public class DistributorController {

    @Autowired
    private CereShopDistributorService cereShopDistributorService;

    @Autowired
    private CereShopDistributionLevelService cereShopDistributionLevelService;

    /**
     * 添加分销员
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加分销员")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated ShopDistributorSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopDistributorService.save(param,user);
        return new Result(user.getUsername(),"添加分销员", GsonUtil.objectToGson(param));
    }

    /**
     * 修改分销员
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改分销员")
    @NoRepeatWebLog
    public Result update(@RequestBody ShopDistributorUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopDistributorService.update(param,user);
        return new Result(user.getUsername(),"修改分销员", GsonUtil.objectToGson(param));
    }

    /**
     * 清退分销员
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "清退分销员")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShopDistributorDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopDistributorService.delete(param,user);
        return new Result(user.getUsername(),"清退分销员", GsonUtil.objectToGson(param));
    }

    /**
     * 分销员修改查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "分销员修改查询")
    public Result<ShopDistributor> getById(@RequestBody ShopDistributorGetByIdParam param) throws CoBusinessException{
        ShopDistributor shopDistributor=cereShopDistributorService.getById(param.getDistributorId());
        return new Result(shopDistributor);
    }

    /**
     * 分销员管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "分销员管理查询")
    public Result<Page<ShopDistributor>> getAll(@RequestBody ShopDistributorGetAllParam param) throws CoBusinessException{
        param.setShopId(String.valueOf(ContextUtil.getShopId()));
        Page page=cereShopDistributorService.getAll(param);
        return new Result(page);
    }

    /**
     * 待审核分销员管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getStayExamineAll",method = RequestMethod.POST)
    @ApiOperation(value = "待审核分销员管理查询")
    public Result<Page<ShopDistributor>> getStayExamineAll(@RequestBody ShopDistributorGetStayParam param) throws CoBusinessException{
        param.setShopId(String.valueOf(ContextUtil.getShopId()));
        Page page=cereShopDistributorService.getStayExamineAll(param);
        return new Result(page);
    }

    /**
     * 分销员申请处理
     * @param param
     * @return
     */
    @RequestMapping(value = "handle",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "分销员申请处理")
    @NoRepeatWebLog
    public Result handle(@RequestBody ShopDistributorHandleParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopDistributorService.handle(param,user);
        return new Result(user.getUsername(),"分销员申请处理", GsonUtil.objectToGson(param));
    }

    /**
     * 查询所有邀请人
     * @param param
     * @return
     */
    @RequestMapping(value = "getAllInvitees",method = RequestMethod.POST)
    @ApiOperation(value = "查询所有邀请人")
    public Result<List<CereShopDistributor>> getAllInvitees(@RequestBody ShopParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<CereShopDistributor> list=cereShopDistributorService.getAllInvitees(param);
        return new Result(list);
    }

    /**
     * 查询所有分销员等级
     * @param param
     * @return
     */
    @RequestMapping(value = "getAllLevel",method = RequestMethod.POST)
    @ApiOperation(value = "查询所有分销员等级")
    public Result<List<CereShopDistributionLevel>> getAllLevel(@RequestBody ShopParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<CereShopDistributionLevel> list=cereShopDistributionLevelService.getAllLevel(param.getShopId());
        return new Result(list);
    }
}
