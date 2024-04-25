/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.permission.Permission;
import com.shop.cereshop.admin.param.permission.*;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.permission.CereBusinessPermissionService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 商家菜单权限
 */
@RestController
@RequestMapping("businessPermission")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BusinessPermissionController")
@Api(value = "菜单权限模块", tags = "商家菜单权限模块")
public class BusinessPermissionController {

    @Autowired
    private CereBusinessPermissionService cereBusinessPermissionService;

    @Autowired
    private StringRedisService stringRedisService;

    /**
     * 同步后台菜单时的key
     */
    private static final String SYNC_MENU_KEY = "sync_menu_ing";

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
        //校验排序值是否已存在
        CerePlatformPermission platformPermission= cereBusinessPermissionService.findBySort(param.getSort(),null);
        if(platformPermission!=null){
            return new Result(CoReturnFormat.SORT_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBusinessPermissionService.save(param,user);
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
        //校验排序值是否已存在
        CerePlatformPermission platformPermission= cereBusinessPermissionService.findBySort(param.getSort(),param.getPermissionId());
        if(platformPermission!=null){
            return new Result(CoReturnFormat.SORT_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBusinessPermissionService.update(param,user);
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
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBusinessPermissionService.delete(param,user);
        return new Result(user.getUsername(),"删除菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 菜单权限编辑查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "菜单权限编辑查询")
    public Result<CerePlatformPermission> getById(@RequestBody PermissionGetByIdParam param) throws CoBusinessException{
        CerePlatformPermission cerePlatformPermission= cereBusinessPermissionService.getById(param.getPermissionId());
        return new Result(cerePlatformPermission);
    }

    /**
     * 查询当前最大排序值
     * @return
     */
    @RequestMapping(value = "getMaxSort",method = RequestMethod.POST)
    @ApiOperation(value = "查询当前最大排序值")
    public Result<Integer> getMaxSort() throws CoBusinessException{
        Integer sort= cereBusinessPermissionService.getMaxSort();
        return new Result(sort);
    }

    /**
     * 菜单权限管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "菜单权限管理查询")
    public Result<Page<Permission>> getAll(@RequestBody PermissionGetAllParam param) throws CoBusinessException{
        Page page = cereBusinessPermissionService.getAll(param);
        return new Result(page);
    }

    /**
     * 菜单权限管理查询
     * @return
     */
    @RequestMapping(value = "syncMenu",method = RequestMethod.POST)
    @ApiOperation(value = "同步菜单")
    public Result<Boolean> syncMenu(@RequestBody SyncMenuParam param) throws CoBusinessException{
        if (param.isSyncAll()) {
            String syncIng = (String)stringRedisService.get(SYNC_MENU_KEY);
            if (StringUtils.isNotBlank(syncIng)) {
                throw new CoBusinessException(CoReturnFormat.SYNC_MENU_ING);
            } else {
                stringRedisService.set(SYNC_MENU_KEY,"on", 300 * 1000);
            }
        }
        cereBusinessPermissionService.syncMenu(param);
        if (param.isSyncAll()) {
            System.out.println("同步完成并删除redis key");
            stringRedisService.delete(SYNC_MENU_KEY);
        }
        return new Result(true);
    }

}
