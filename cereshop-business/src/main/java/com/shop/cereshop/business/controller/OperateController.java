/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.operate.OperateData;
import com.shop.cereshop.business.page.operate.ShopOperate;
import com.shop.cereshop.business.page.tool.ShopCrowd;
import com.shop.cereshop.business.page.tool.ShopCrowdDetail;
import com.shop.cereshop.business.page.tool.ShopOperateDetail;
import com.shop.cereshop.business.param.tool.*;
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
 * 客户运营
 */
@RestController
@RequestMapping("operate")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "OperateController")
@Api(value = "客户运营模块", tags = "客户运营模块")
public class OperateController {

    @Autowired
    private CereShopOperateService cereShopOperateService;

    @Autowired
    private CereShopCrowdService cereShopCrowdService;

    /**
     * 新增运营计划
     * @param param
     * @return
     */
    @RequestMapping(value = "saveOperate",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增运营计划")
    @NoRepeatWebLog
    public Result saveOperate(@RequestBody ShopOperateSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopOperateService.saveOperate(param,user);
        return new Result(user.getUsername(),"新增运营计划", GsonUtil.objectToGson(param));
    }

    /**
     * 修改运营计划
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改运营计划")
    @NoRepeatWebLog
    public Result update(@RequestBody ShopOperateUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereShopOperateService.update(param,user);
        return new Result(user.getUsername(),"修改运营计划", GsonUtil.objectToGson(param));
    }

    /**
     * 删除运营计划
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除运营计划")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShopOperateGetByIdParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopOperateService.delete(param.getIds(),user);
        return new Result(user.getUsername(),"删除运营计划", GsonUtil.objectToGson(param));
    }

    /**
     * 运营计划详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "运营计划详情查询")
    public Result<ShopCrowdDetail> getById(@RequestBody ShopOperateGetByIdParam param) throws CoBusinessException{
        ShopOperateDetail detail=cereShopOperateService.getById(param.getShopOperateId());
        return new Result(detail);
    }

    /**
     * 运营计划列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "运营计划列表查询")
    public Result<Page<ShopOperate>> getAll(@RequestBody ShopOperateGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page=cereShopOperateService.getAll(param);
        return new Result(page);
    }

    /**
     * 数据查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getDatas",method = RequestMethod.POST)
    @ApiOperation(value = "数据查询")
    public Result<List<OperateData>> getDatas(@RequestBody ShopOperateGetByIdParam param) throws CoBusinessException,Exception {
        List<OperateData> list=cereShopOperateService.getDatas(param.getShopOperateId());
        return new Result(list);
    }

    /**
     * 选择人群查询
     * @return
     */
    @RequestMapping(value = "selectCrowd",method = RequestMethod.POST)
    @ApiOperation(value = "选择人群查询")
    public Result<Page<ShopCrowd>> selectCrowd(@RequestBody ShopCrowdGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopCrowdService.selectCrowd(param);
        return new Result(page);
    }

    /**
     * 选择优惠券查询
     * @return
     */
    @RequestMapping(value = "selectCoupon",method = RequestMethod.POST)
    @ApiOperation(value = "选择优惠券查询")
    public Result<Page<OperateCoupon>> selectCoupon(@RequestBody OperateCouponParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopOperateService.selectCoupon(param);
        return new Result(page);
    }
}

