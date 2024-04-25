/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.param.dict.DictGetSelectParam;
import com.shop.cereshop.business.service.dict.CerePlatformDictService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典
 */
@RestController
@RequestMapping("dict")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "DictController")
@Api(value = "数据字典模块", tags = "数据字典模块")
public class DictController {

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    /**
     * 字典下拉数据查询
     * @return
     */
    @RequestMapping(value = "getSelect",method = RequestMethod.POST)
    @ApiOperation(value = "字典下拉数据查询")
    public Result<List<CerePlatformDict>> getSelect(@RequestBody DictGetSelectParam param) throws CoBusinessException{
        List<CerePlatformDict> list =cerePlatformDictService.getSelect(param.getDictName());
        return new Result(list);
    }
}
