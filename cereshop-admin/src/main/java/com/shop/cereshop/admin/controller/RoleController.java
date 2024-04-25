/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.permission.RolePermission;
import com.shop.cereshop.admin.param.role.*;
import com.shop.cereshop.admin.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.admin.service.role.CerePlatformRoleService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
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
 * 角色
 */
@RestController
@RequestMapping("role")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "RoleController")
@Api(value = "角色模块", tags = "角色模块")
public class RoleController {

    @Autowired
    private CerePlatformRoleService cerePlatformRoleService;

    @Autowired
    private CerePlatformPermissionService cerePlatformPermissionService;

    /**
     * 添加角色
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加角色")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated RoleSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.save(param,user);
        return new Result(user.getUsername(),"添加角色", GsonUtil.objectToGson(param));
    }

    /**
     * 修改角色
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改角色")
    @NoRepeatWebLog
    public Result update(@RequestBody RoleUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.update(param,user);
        return new Result(user.getUsername(),"修改角色", GsonUtil.objectToGson(param));
    }

    /**
     * 删除角色
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除角色")
    @NoRepeatWebLog
    public Result delete(@RequestBody RoleDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.delete(param,user);
        return new Result(user.getUsername(),"删除角色", GsonUtil.objectToGson(param));
    }

    /**
     * 角色编辑查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "角色编辑查询")
    public Result<CerePlatformRole> getById(@RequestBody RoleGetByIdParam role) throws CoBusinessException{
        CerePlatformRole cerePlatformRole=cerePlatformRoleService.getById(role.getRoleId());
        return new Result(cerePlatformRole);
    }

    /**
     * 角色管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "角色管理查询")
    public Result<Page<CerePlatformRole>> getAll(@RequestBody RoleGetAllParam param) throws CoBusinessException{
        Page page =cerePlatformRoleService.getAll(param);
        return new Result(page);
    }

    /**
     * 菜单分配
     * @return
     */
    @RequestMapping(value = "distribution",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "菜单分配")
    @NoRepeatWebLog
    public Result distribution(@RequestBody RoleDistributionParam param,HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.distribution(param,user);
        return new Result(user.getUsername(),"菜单分配", GsonUtil.objectToGson(param));
    }

    /**
     * 菜单分配查询
     * @return
     */
    @RequestMapping(value = "getRolePermission",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "菜单分配查询")
    public Result<RolePermission> getRolePermission(@RequestBody RoleDistributionParam param) throws CoBusinessException{
        RolePermission rolePermission=cerePlatformPermissionService.getRolePermission(param);
        return new Result(rolePermission);
    }
}
