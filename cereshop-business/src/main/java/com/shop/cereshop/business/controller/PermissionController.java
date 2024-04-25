/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.permission.Permission;
import com.shop.cereshop.business.param.permission.*;
import com.shop.cereshop.business.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
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
 * 菜单权限
 */
@RestController
@RequestMapping("permission")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "PermissionController")
@Api(value = "菜单权限模块", tags = "菜单权限模块")
public class PermissionController {

    @Autowired
    private CerePlatformPermissionService cerePlatformPermissionService;

    /**
     * 添加菜单权限
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加菜单权限")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated PermissionSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        //校验排序值是否已存在
        CerePlatformPermission platformPermission=cerePlatformPermissionService.findBySort(param.getSort(),null,user.getShopId());
        if(platformPermission!=null){
            return new Result(CoReturnFormat.SORT_ALREADY);
        }
        cerePlatformPermissionService.save(param,user);
        return new Result(user.getUsername(),"添加菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 修改菜单权限
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改菜单权限")
    @NoRepeatWebLog
    public Result update(@RequestBody PermissionUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        //校验排序值是否已存在
        CerePlatformPermission platformPermission=cerePlatformPermissionService.findBySort(param.getSort(),param.getPermissionId(),user.getShopId());
        if(platformPermission!=null){
            return new Result(CoReturnFormat.SORT_ALREADY);
        }
        cerePlatformPermissionService.update(param,user);
        return new Result(user.getUsername(),"修改菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 删除菜单权限
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除菜单权限")
    @NoRepeatWebLog
    public Result delete(@RequestBody PermissionDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cerePlatformPermissionService.delete(param,user);
        return new Result(user.getUsername(),"删除菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 菜单权限编辑查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "菜单权限编辑查询")
    public Result<CerePlatformPermission> getById(@RequestBody PermissionGetByIdParam param) throws CoBusinessException{
        CerePlatformPermission cerePlatformPermission=cerePlatformPermissionService.getById(param.getPermissionId());
        return new Result(cerePlatformPermission);
    }

    /**
     * 查询当前最大排序值
     * @return
     */
    @RequestMapping(value = "getMaxSort",method = RequestMethod.POST)
    @ApiOperation(value = "查询当前最大排序值")
    public Result<Integer> getMaxSort() throws CoBusinessException{
        Integer sort=cerePlatformPermissionService.getMaxSort();
        return new Result(sort);
    }

    /**
     * 菜单权限管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "菜单权限管理查询")
    public Result<Page<Permission>> getAll(@RequestBody PermissionGetAllParam param,HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cerePlatformPermissionService.getAll(param);
        return new Result(page);
    }

}
