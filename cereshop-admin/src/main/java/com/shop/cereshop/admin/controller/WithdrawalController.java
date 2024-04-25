/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalGetByIdParam;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalHandleParam;
import com.shop.cereshop.admin.service.shop.CereShopWithdrawalService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.CereShopWithdrawal;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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

/**
 * 提现申请
 */
@RestController
@RequestMapping("withdrawal")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "WithdrawalController")
@Api(value = "提现申请模块", tags = "提现申请模块")
public class WithdrawalController {

    @Autowired
    private CereShopWithdrawalService cereShopWithdrawalService;

    /**
     * 提现申请查看
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "提现申请查看")
    public Result<CereShopWithdrawal> getById(@RequestBody WithdrawalGetByIdParam param) throws CoBusinessException{
        CereShopWithdrawal withdrawal =cereShopWithdrawalService.getById(param.getWithdrawalId());
        return new Result(withdrawal);
    }

    /**
     * 提现申请处理
     * @return
     */
    @RequestMapping(value = "handle",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "提现申请处理")
    @NoRepeatWebLog
    public Result handle(@RequestBody WithdrawalHandleParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopWithdrawalService.handle(param,user);
        return new Result(user.getUsername(),"提现申请处理", GsonUtil.objectToGson(param));
    }

    /**
     * 提现申请管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "提现申请管理查询")
    public Result<Page<CereShopWithdrawal>> getAll(@RequestBody WithdrawalGetAllParam param) throws CoBusinessException{
        Page page =cereShopWithdrawalService.getAll(param);
        return new Result(page);
    }
}
