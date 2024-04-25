/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.finance.*;
import com.shop.cereshop.business.param.finance.*;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.activity.CereActivitySignService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.shop.CereShopWithdrawalService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 财务
 */
@RestController
@RequestMapping("finance")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "FinanceController")
@Api(value = "财务模块", tags = "财务模块")
public class FinanceController {

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Autowired
    private CereShopWithdrawalService cereShopWithdrawalService;

    @Autowired
    private CereActivitySignService cereActivitySignService;

    /**
     * 财务统计数据查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getFinanceCount",method = RequestMethod.POST)
    @ApiOperation(value = "财务统计数据查询")
    public Result<FinanceCount> getFinanceCount(@RequestBody FinanceCountParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        FinanceCount count=cereShopOrderService.getFinanceCount(param);
        return new Result(count);
    }

    /**
     * 查询绑定银行卡
     * @param param
     * @return
     */
    @RequestMapping(value = "getBank",method = RequestMethod.POST)
    @ApiOperation(value = "查询绑定银行卡")
    public Result<Bank> getBank(@RequestBody ShopParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Bank bank=cereShopOrderService.getBank(param.getShopId());
        return new Result(bank);
    }

    /**
     * 提现明细查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getWithdrawalDetails",method = RequestMethod.POST)
    @ApiOperation(value = "提现明细查询")
    public Result<List<WithdrawalDetail>> getWithdrawalDetails(@RequestBody FinanceWithdrawalParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<WithdrawalDetail> list=cereShopOrderService.getWithdrawalDetails(param);
        return new Result(list);
    }

    /**
     * 流水明细
     * @param param
     * @return
     */
    @RequestMapping(value = "getDetails",method = RequestMethod.POST)
    @ApiOperation(value = "流水明细")
    public Result<Page<FlowingWater>> getDetails(@RequestBody FinanceDetailParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopOrderService.getDetails(param);
        return new Result(page);
    }

    /**
     * 提现申请
     * @param withdrawal
     * @return
     */
    @RequestMapping(value = "withdrawal",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "提现申请")
    @NoRepeatWebLog
    public Result withdrawal(@RequestBody FinanceSaveWithdralwalParam withdrawal, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        withdrawal.setShopId(ContextUtil.getShopId());
        cereShopWithdrawalService.save(withdrawal,user);
        return new Result(user.getUsername(),"提现申请", GsonUtil.objectToGson(withdrawal));
    }

    /**
     * 保证金管理查询
     * @return
     */
    @RequestMapping(value = "getAllBond",method = RequestMethod.POST)
    @ApiOperation(value = "保证金管理查询")
    public Result<BondCount> getAllBond(@RequestBody BondParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        BondCount count =cereActivitySignService.getAllBond(param);
        return new Result(count);
    }
}
