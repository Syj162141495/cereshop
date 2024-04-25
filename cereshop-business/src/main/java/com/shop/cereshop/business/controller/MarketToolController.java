/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.page.activity.MarketTool;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.service.activity.CereShopMarketToolService;
import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
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
import java.util.List;

/**
 * 营销工具信息
 */
@RestController
@RequestMapping("tool")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "MarketToolController")
@Api(value = "营销工具模块", tags = "营销工具模块")
public class MarketToolController {

    @Autowired
    private CereShopMarketToolService cereShopMarketToolService;

    /**
     * 添加营销工具
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加营销工具")
    public Result save(@RequestBody ToolSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopMarketToolService.save(param,user);
        return new Result();
    }

    /**
     * 修改营销工具
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改营销工具")
    public Result update(@RequestBody ToolUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopMarketToolService.update(param,user);
        return new Result();
    }

    /**
     * 删除营销工具
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除营销工具")
    public Result delete(@RequestBody ToolDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopMarketToolService.delete(param,user);
        return new Result();
    }

    /**
     * 结束营销工具
     * @param param
     * @return
     */
    @RequestMapping(value = "end",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "结束营销工具")
    public Result end(@RequestBody ToolEndParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopMarketToolService.end(param,user);
        return new Result();
    }

    /**
     * 营销工具详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "营销工具详情查询")
    public Result<MarketTool> getById(@RequestBody ToolGetByIdParam param) throws CoBusinessException{
        MarketTool marketTool=cereShopMarketToolService.getById(param.getToolId());
        return new Result(marketTool);
    }

    /**
     * 营销工具管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "营销工具管理查询")
    public Result<List<MarketTool>> getAll(@RequestBody ToolGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<MarketTool> list =cereShopMarketToolService.getAll(param);
        return new Result(list);
    }
}
