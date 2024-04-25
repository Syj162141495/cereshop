/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.service.dict.CerePlatformDictService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物流
 */
@RestController
@RequestMapping("express")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ExpressController")
@Api(value = "物流", tags = "物流")
public class ExpressController {

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    /**
     * 选择物流公司查询
     * @return
     */
    @RequestMapping(value = "getExpressSelect",method = RequestMethod.POST)
    @ApiOperation(value = "选择物流公司查询")
    public Result<List<CerePlatformDict>> getExpressSelect() throws CoBusinessException{
        List<CerePlatformDict> list =cerePlatformDictService.getExpressSelect();
        return new Result(list);
    }
}
