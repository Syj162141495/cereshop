/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.group.Group;
import com.shop.cereshop.business.page.group.GroupDetail;
import com.shop.cereshop.business.page.group.GroupProduct;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.group.*;
import com.shop.cereshop.business.service.shop.CereShopGroupService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
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
import java.util.List;

/**
 * 店铺商品分组
 */
@RestController
@RequestMapping("group")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopGroupController")
@Api(value = "店铺商品分组模块", tags = "店铺商品分组模块")
public class ShopGroupController {

    @Autowired
    private CereShopGroupService cereShopGroupService;

    /**
     * 添加分组
     * @param group
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加分组")
    @NoRepeatWebLog
    public Result save(@RequestBody ShopGroupSaveParam group, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        group.setShopId(ContextUtil.getShopId());
        cereShopGroupService.save(group,user);
        return new Result(user.getUsername(),"添加分组", GsonUtil.objectToGson(group));
    }

    /**
     * 获取满足条件的商品id
     * @param group
     * @return
     */
    @RequestMapping(value = "getProductIds",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "获取满足条件的商品id")
    public Result<List<Product>> getProductIds(@RequestBody ShopGroupSaveParam group) throws CoBusinessException{
        group.setShopId(ContextUtil.getShopId());
        List<Product> list=cereShopGroupService.getProductIds(group);
        return new Result(list);
    }

    /**
     * 修改分组
     * @param group
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改分组")
    @NoRepeatWebLog
    public Result update(@RequestBody ShopGroupUpdateParam group, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        group.setShopId(ContextUtil.getShopId());
        cereShopGroupService.update(group,user);
        return new Result(user.getUsername(),"修改分组", GsonUtil.objectToGson(group));
    }

    /**
     * 删除分组
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除分组")
    @NoRepeatWebLog
    public Result delete(@RequestBody GroupDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopGroupService.delete(param,user);
        return new Result(user.getUsername(),"删除分组", GsonUtil.objectToGson(param));
    }

    /**
     * 分组修改查询
     * @param group
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "分组修改查询")
    public Result<GroupDetail> getById(@RequestBody GroupDeleteParam group) throws CoBusinessException{
        GroupDetail detail=cereShopGroupService.getById(group.getShopGroupId());
        return new Result(detail);
    }

    /**
     * 分组管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "分组管理查询")
    public Result<Page<Group>> getAll(@RequestBody GroupGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopGroupService.getAll(param);
        return new Result(page);
    }

    /**
     * 手动添加商品查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "手动添加商品查询")
    public Result<Page<GroupProduct>> getProducts(@RequestBody GroupProductParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopGroupService.getProducts(param);
        return new Result(page);
    }
}
