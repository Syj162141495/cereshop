/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.after.After;
import com.shop.cereshop.admin.page.after.AfterBuyer;
import com.shop.cereshop.admin.page.after.AfterDetail;
import com.shop.cereshop.admin.page.after.AfterDilevery;
import com.shop.cereshop.admin.param.after.*;
import com.shop.cereshop.admin.service.after.CereOrderAfterService;
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
import java.util.List;

/**
 * 售后管理
 */
@RestController
@RequestMapping("after")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "AfterController")
@Api(value = "售后管理模块", tags = "售后管理模块")
public class AfterController {

    @Autowired
    private CereOrderAfterService cereOrderAfterService;

    /**
     * 售后管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "售后管理查询")
    public Result<Page<After>> getAll(@RequestBody AfterGetAllParam param) throws CoBusinessException{
        Page page=cereOrderAfterService.getAll(param);
        return new Result(page);
    }

    /**
     * 售后详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "售后详情查询")
    public Result<AfterDetail> getById(@RequestBody AfterGetByIdParam param) throws CoBusinessException,Exception{
        AfterDetail detail=cereOrderAfterService.getById(param.getAfterId());
        return new Result(detail);
    }

    /**
     * 物流信息查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getDilevery",method = RequestMethod.POST)
    @ApiOperation(value = "物流信息查询")
    public Result<List<AfterDilevery>> getDilevery(@RequestBody AfterGetDileveryParam param) throws CoBusinessException,Exception{
        List<AfterDilevery> dileveries=cereOrderAfterService.getDilevery(param);
        return new Result(dileveries);
    }

    /**
     * 买家信息查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getBuyer",method = RequestMethod.POST)
    @ApiOperation(value = "买家信息查询")
    public Result<AfterBuyer> getBuyer(@RequestBody AfterGetBuyerParam param) throws CoBusinessException{
        AfterBuyer buyer=cereOrderAfterService.getBuyer(param.getBuyerUserId());
        return new Result(buyer);
    }

    /**
     * 售后处理
     * @param param
     * @return
     */
    @RequestMapping(value = "handle",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "售后处理")
    @NoRepeatWebLog
    public Result handle(@RequestBody AfterhandleParam param,
                         HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereOrderAfterService.handle(param,user);
        return new Result(user.getUsername(),"售后处理", GsonUtil.objectToGson(param));
    }
}
