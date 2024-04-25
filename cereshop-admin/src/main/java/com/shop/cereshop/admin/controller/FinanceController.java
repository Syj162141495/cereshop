/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.page.finance.BondCount;
import com.shop.cereshop.admin.page.finance.FinanceCount;
import com.shop.cereshop.admin.page.finance.ShopBond;
import com.shop.cereshop.admin.param.finance.BondParam;
import com.shop.cereshop.admin.param.finance.FinanceParam;
import com.shop.cereshop.admin.service.activity.CereActivitySignService;
import com.shop.cereshop.admin.service.shop.CerePlatformShopService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
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
 * 财务管理
 */
@RestController
@RequestMapping("finance")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "FinanceController")
@Api(value = "财务管理模块", tags = "财务管理模块")
public class FinanceController {

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    @Autowired
    private CereActivitySignService cereActivitySignService;

    /**
     * 财务管理查询
     * @return
     */
    @RequestMapping(value = "getAllFinance",method = RequestMethod.POST)
    @ApiOperation(value = "财务管理查询")
    public Result<FinanceCount> getAllFinance(@RequestBody FinanceParam param) throws CoBusinessException{
        FinanceCount count =cerePlatformShopService.getAllFinance(param);
        return new Result(count);
    }

    /**
     * 保证金管理查询
     * @return
     */
    @RequestMapping(value = "getAllBond",method = RequestMethod.POST)
    @ApiOperation(value = "保证金管理查询")
    public Result<BondCount> getAllBond(@RequestBody BondParam param) throws CoBusinessException{
        BondCount count =cereActivitySignService.getAllBond(param);
        return new Result(count);
    }
}
