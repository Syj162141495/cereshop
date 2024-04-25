/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.service.init.InitPermissionService;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化版本迭代新增菜单
 */
@RestController
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "InitPermissionController")
public class InitPermissionController {

    @Autowired
    private InitPermissionService initPermissionService;

    /**
     * 初始化1.4版本新增商家端菜单
     * @return
     */
    @RequestMapping(value = "initPermission",method = RequestMethod.POST)
    public Result getAll() throws CoBusinessException{
        initPermissionService.initPermission();
        return new Result();
    }

}
