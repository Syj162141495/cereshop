/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.service.mark.MarkDataService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 造数据
 */
@RestController
@RequestMapping("mark")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "MakingDataController")
public class MakingDataController {

    @Autowired
    private MarkDataService markDataService;

    /**
     * 创建订单
     * @return
     */
    @RequestMapping(value = "createOrder",method = RequestMethod.POST)
    @NoRepeatSubmit
    public Result createOrder() throws CoBusinessException{
        markDataService.createOrder();
        return new Result();
    }

    /**
     * 创建售后单
     * @return
     */
    @RequestMapping(value = "createAfter",method = RequestMethod.POST)
    @NoRepeatSubmit
    public Result createAfter() throws CoBusinessException{
        markDataService.createAfter();
        return new Result();
    }
}
