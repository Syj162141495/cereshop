/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.city.City;
import com.shop.cereshop.business.page.logistics.Logistics;
import com.shop.cereshop.business.param.logistics.*;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.city.CereCityManageService;
import com.shop.cereshop.business.service.logistics.CereOrderLogisticsService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
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
import java.util.List;

/**
 * 物流方案
 */
@RestController
@RequestMapping("logistics")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "LogisticsController")
@Api(value = "物流方案模块", tags = "物流方案模块")
public class LogisticsController {

    @Autowired
    private CereOrderLogisticsService cereOrderLogisticsService;

    @Autowired
    private CereCityManageService cereCityManageService;

    /**
     * 添加物流方案
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加物流方案")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated LogistSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereOrderLogisticsService.save(param,user);
        return new Result(user.getUsername(),"添加物流方案", GsonUtil.objectToGson(param));
    }

    /**
     * 修改物流方案
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改物流方案")
    @NoRepeatWebLog
    public Result update(@RequestBody LogistUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereOrderLogisticsService.update(param,user);
        return new Result(user.getUsername(),"修改物流方案", GsonUtil.objectToGson(param));
    }

    /**
     * 删除物流方案
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除物流方案")
    @NoRepeatWebLog
    public Result delete(@RequestBody LogistDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderLogisticsService.delete(param,user);
        return new Result(user.getUsername(),"删除物流方案", GsonUtil.objectToGson(param));
    }

    /**
     * 物流方案修改查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "物流方案修改查询")
    public Result<Logistics> getById(@RequestBody LogistGetByIdParam param) throws CoBusinessException{
        Logistics logistics=cereOrderLogisticsService.getById(param.getLogisticsId());
        return new Result(logistics);
    }

    /**
     * 物流方案管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "物流方案管理查询")
    public Result<Page<Logistics>> getAll(@RequestBody ShopParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereOrderLogisticsService.getAll(param);
        return new Result(page);
    }

    /**
     * 选择城市查询（根据上级节点查询下级节点数据）
     * @return
     */
    @RequestMapping(value = "getCitySelect",method = RequestMethod.POST)
    @ApiOperation(value = "选择城市查询（根据上级节点查询下级节点数据）")
    public Result<List<City>> getCitySelect(@RequestBody LogistCityParam param) throws CoBusinessException{
        List<City> list=cereCityManageService.getCitySelect(param);
        return new Result(list);
    }
}
