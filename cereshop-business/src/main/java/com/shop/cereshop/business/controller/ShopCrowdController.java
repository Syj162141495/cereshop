/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.tool.ShopCrowd;
import com.shop.cereshop.business.page.tool.ShopCrowdDetail;
import com.shop.cereshop.business.param.tool.ShopCrowdGetAllParam;
import com.shop.cereshop.business.param.tool.ShopCrowdGetByIdParam;
import com.shop.cereshop.business.param.tool.ShopCrowdSaveParam;
import com.shop.cereshop.business.param.tool.ShopCrowdUpdateParam;
import com.shop.cereshop.business.service.tool.CereShopCrowdService;
import com.shop.cereshop.business.service.tool.CereShopOperateService;
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
 * 店铺人群
 */
@RestController
@RequestMapping("crowd")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopCrowdController")
@Api(value = "店铺人群模块", tags = "店铺人群模块")
public class ShopCrowdController {

    @Autowired
    private CereShopCrowdService cereShopCrowdService;

    @Autowired
    private CereShopOperateService cereShopOperateService;

    /**
     * 新增人群
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增人群")
    @NoRepeatWebLog
    public Result save(@RequestBody ShopCrowdSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopCrowdService.save(param,user);
        return new Result(user.getUsername(),"新增人群", GsonUtil.objectToGson(param));
    }

    /**
     * 修改人群
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改人群")
    @NoRepeatWebLog
    public Result update(@RequestBody ShopCrowdUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopCrowdService.update(param,user);
        return new Result(user.getUsername(),"修改人群", GsonUtil.objectToGson(param));
    }

    /**
     * 删除人群
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除人群")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShopCrowdGetByIdParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopCrowdService.delete(param,user);
        return new Result(user.getUsername(),"删除人群", GsonUtil.objectToGson(param));
    }

    /**
     * 人群详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "人群详情查询")
    public Result<ShopCrowdDetail> getById(@RequestBody ShopCrowdGetByIdParam param) throws CoBusinessException{
        ShopCrowdDetail detail=cereShopCrowdService.getById(param.getShopCrowdId());
        return new Result(detail);
    }

    /**
     * 人群列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "人群列表查询")
    public Result<Page<ShopCrowd>> getAll(@RequestBody ShopCrowdGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopCrowdService.getAll(param);
        return new Result(page);
    }

    /**
     * 查看客户
     * @param param
     * @return
     */
    @RequestMapping(value = "getUsers",method = RequestMethod.POST)
    @ApiOperation(value = "查看客户")
    public Result<List<BuyerUser>> getUsers(@RequestBody ShopCrowdGetByIdParam param) throws CoBusinessException {
        List<BuyerUser> list=cereShopCrowdService.getUsers(param.getShopCrowdId());
        return new Result(list);
    }
}

