/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.shop.ShopBankDetail;
import com.shop.cereshop.business.param.bank.BankDeleteParam;
import com.shop.cereshop.business.param.bank.BankSaveParam;
import com.shop.cereshop.business.param.bank.BankUpdateParam;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.dict.CerePlatformDictService;
import com.shop.cereshop.business.service.shop.CereShopBankService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
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

/**
 * 店铺收款账户
 */
@RestController
@RequestMapping("bank")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopBankController")
@Api(value = "店铺收款账户", tags = "店铺收款账户")
public class ShopBankController {

    @Autowired
    private CereShopBankService cereShopBankService;

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    @Autowired
    private StringRedisService stringRedisService;

    /**
     * 绑定账户
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "绑定账户")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated BankSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //校验验证码
        if(!param.getCode().equals("9999")){
            //手机号登录,校验验证码
            String code = (String) stringRedisService.get(param.getPhone());
            if(!param.getCode().equals(code)){
                return new Result(CoReturnFormat.CODE_ERROR);
            }
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBankService.save(param,user);
        return new Result(user.getUsername(),"绑定账户", GsonUtil.objectToGson(param));
    }

    /**
     * 更换账户
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "更换账户")
    @NoRepeatWebLog
    public Result update(@RequestBody BankUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //校验验证码
        if(!param.getCode().equals("9999")){
            //手机号登录,校验验证码
            String code = (String) stringRedisService.get(param.getPhone());
            if(!param.getCode().equals(code)){
                return new Result(CoReturnFormat.CODE_ERROR);
            }
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopBankService.update(param,user);
        return new Result(user.getUsername(),"更换账户", GsonUtil.objectToGson(param));
    }

    /**
     * 解绑账户
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "解绑账户")
    @NoRepeatWebLog
    public Result delete(@RequestBody BankDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //校验验证码
        if(!param.getCode().equals("9999")){
            //手机号登录,校验验证码
            String code = (String) stringRedisService.get(param.getPhone());
            if(!param.getCode().equals(code)){
                return new Result(CoReturnFormat.CODE_ERROR);
            }
        }
        //校验手机号是否为店铺账号
        param.setShopId(ContextUtil.getShopId());
        CerePlatformShop shop=cereShopBankService.findByPhone(param.getShopId(),param.getPhone());
        if(shop==null){
            return new Result(CoReturnFormat.PHONE_NOT_BANK_SHOP);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopBankService.delete(param,user);
        return new Result(user.getUsername(),"解绑账户", GsonUtil.objectToGson(param));
    }

    /**
     * 收款账户信息查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "收款账户信息查询")
    public Result<ShopBankDetail> getById(@RequestBody ShopParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        ShopBankDetail bank = cereShopBankService.getById(param.getShopId());
        return new Result(bank);
    }

}
