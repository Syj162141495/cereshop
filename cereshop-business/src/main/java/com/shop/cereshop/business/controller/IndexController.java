/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.page.index.Index;
import com.shop.cereshop.business.param.index.IndexParam;
import com.shop.cereshop.business.service.shop.CerePlatformShopService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
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

import javax.servlet.http.HttpServletRequest;

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
     * @param param
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.POST)
    @ApiOperation(value = "商户端首页概况数据查询")
    public Result<Index> index(@RequestBody IndexParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Index index=cerePlatformShopService.index(param);
        return new Result(index);
    }

    /**
     * 商户端首页概况数据查询
     * @param param
     * @return
     */
//    @RequestMapping(value = "index",method = RequestMethod.POST)
//    @ApiOperation(value = "商户端首页概况数据查询")
//    public Result<Index> index(@RequestBody IndexParam param, HttpServletRequest request) throws CoBusinessException{
//        Index index=cerePlatformShopService.indexTest(param);
//        return new Result(index);
//    }
}
