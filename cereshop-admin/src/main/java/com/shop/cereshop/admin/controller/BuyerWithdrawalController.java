/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.buyer.BuyerWithdrawal;
import com.shop.cereshop.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.cereshop.admin.param.withdrawal.BuyerWithdrawalGetByIdParam;
import com.shop.cereshop.admin.service.buyer.CereBuyerWithdrawalService;
import com.shop.cereshop.commons.domain.common.Page;
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
 * 用户提现
 */
@RestController
@RequestMapping("buyer_withdrawal")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BuyerWithdrawalController")
@Api(value = "用户提现", tags = "用户提现")
public class BuyerWithdrawalController {

    @Autowired
    private CereBuyerWithdrawalService cereBuyerWithdrawalService;

    /**
     * 用户提现申请管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "用户提现申请管理查询")
    public Result<Page<BuyerWithdrawal>> getAll(@RequestBody BuyerWithdrawalGetAllParam param) throws CoBusinessException{
        Page page =cereBuyerWithdrawalService.getAll(param);
        return new Result(page);
    }

    /**
     * 查看
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "查看")
    public Result<BuyerWithdrawal> getById(@RequestBody BuyerWithdrawalGetByIdParam param) throws CoBusinessException{
        BuyerWithdrawal withdrawal =cereBuyerWithdrawalService.getById(param.getWithdrawalId());
        return new Result(withdrawal);
    }

    /**
     * 提现处理
     * @return
     */
    @RequestMapping(value = "handle",method = RequestMethod.POST)
    @ApiOperation(value = "提现处理")
    @NoRepeatWebLog
    public Result handle(@RequestBody BuyerWithdrawalGetByIdParam param,
                         HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerWithdrawalService.handle(param,user);
        return new Result(user.getUsername(),"提现处理", GsonUtil.objectToGson(param));
    }
}
