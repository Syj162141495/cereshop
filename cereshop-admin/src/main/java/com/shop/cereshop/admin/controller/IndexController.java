/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.page.index.Index;
import com.shop.cereshop.admin.service.shop.CerePlatformShopService;
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

/**
 * 首页概况
 */
@RestController
@RequestMapping("index")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "IndexController")
@Api(value = "首页概况", tags = "首页概况")
public class IndexController {

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    /**
     * 商户端首页概况数据查询
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.POST)
    @ApiOperation(value = "商户端首页概况数据查询")
    public Result<Index> index() throws CoBusinessException{
        Index index=cerePlatformShopService.getIndex();
        return new Result(index);
    }
}
