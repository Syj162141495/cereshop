/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.shop.ShopRelationship;
import com.shop.cereshop.business.param.ship.*;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.distributor.CereDistributorBuyerService;
import com.shop.cereshop.business.service.shop.CereShopRelationshipService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.CereShopRelationship;
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
 * 关系设置
 */
@RestController
@RequestMapping("ship")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "RelationshipController")
@Api(value = "关系设置模块", tags = "关系设置模块")
public class RelationshipController {

    @Autowired
    private CereShopRelationshipService cereShopRelationshipService;

    @Autowired
    private CereDistributorBuyerService cereDistributorBuyerService;

    /**
     * 添加关系设置
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加关系设置")
    @NoRepeatWebLog
    public Result save(@RequestBody ShipSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopRelationshipService.save(param,user);
        return new Result(user.getUsername(),"添加关系设置", GsonUtil.objectToGson(param));
    }

    /**
     * 修改关系设置
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改关系设置")
    @NoRepeatWebLog
    public Result update(@RequestBody ShipUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopRelationshipService.update(param,user);
        return new Result(user.getUsername(),"修改关系设置", GsonUtil.objectToGson(param));
    }

    /**
     * 关系设置查询
     * @param relationship
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "关系设置查询")
    public Result<CereShopRelationship> getById(@RequestBody ShopParam relationship) throws CoBusinessException{
        relationship.setShopId(ContextUtil.getShopId());
        CereShopRelationship cereShopRelationship=cereShopRelationshipService.getById(relationship.getShopId());
        return new Result(cereShopRelationship);
    }

    /**
     * 关系管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "关系管理查询")
    public Result<Page<ShopRelationship>> getAll(@RequestBody ShipGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopRelationshipService.getAll(param);
        return new Result(page);
    }


    /**
     * 绑定关系
     * @param buyer
     * @return
     */
    @RequestMapping(value = "bind",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "绑定关系")
    @NoRepeatWebLog
    public Result bind(@RequestBody ShipBindParam buyer, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        buyer.setShopId(ContextUtil.getShopId());
        cereDistributorBuyerService.bind(buyer,user);
        return new Result(user.getUsername(),"绑定关系", GsonUtil.objectToGson(buyer));
    }

    /**
     * 解除绑定关系
     * @param buyer
     * @return
     */
    @RequestMapping(value = "relieveBind",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "解除绑定关系")
    @NoRepeatWebLog
    public Result relieveBind(@RequestBody ShipRelieveParam buyer, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereDistributorBuyerService.relieveBind(buyer,user);
        return new Result(user.getUsername(),"解除绑定关系", GsonUtil.objectToGson(buyer));
    }

    /**
     * 移除绑定关系
     * @param buyer
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "移除绑定关系")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShipDeleteParam buyer, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        buyer.setShopId(ContextUtil.getShopId());
        cereDistributorBuyerService.delete(buyer,user);
        return new Result(user.getUsername(),"移除绑定关系", GsonUtil.objectToGson(buyer));
    }
}
