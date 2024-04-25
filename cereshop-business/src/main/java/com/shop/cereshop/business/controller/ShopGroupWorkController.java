/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.tool.ShopGroupWork;
import com.shop.cereshop.business.page.tool.ShopGroupWorkData;
import com.shop.cereshop.business.page.tool.ShopGroupWorkUDetail;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.service.product.CereProductSkuService;
import com.shop.cereshop.business.service.tool.CereShopGroupWorkService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EmptyUtils;
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
 * 店铺拼团活动管理
 */
@RestController
@RequestMapping("work")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopGroupWorkController")
@Api(value = "店铺拼团活动管理模块", tags = "店铺拼团活动管理模块")
public class ShopGroupWorkController {

    @Autowired
    private CereShopGroupWorkService cereShopGroupWorkService;

    @Autowired
    private CereProductSkuService cereProductSkuService;

    /**
     * 新增拼团活动
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增拼团活动")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated ShopGroupWorkSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        if(EmptyUtils.isEmpty(param.getDetails())){
            throw new CoBusinessException(CoReturnFormat.HAVE_ONE_TOOL_PRODUCT);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopGroupWorkService.save(param,user);
        return new Result(user.getUsername(),"新增拼团活动", GsonUtil.objectToGson(param));
    }

    /**
     * 修改拼团活动
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改拼团活动")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated ShopGroupWorkUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        if(EmptyUtils.isEmpty(param.getDetails())){
            throw new CoBusinessException(CoReturnFormat.HAVE_ONE_TOOL_PRODUCT);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopGroupWorkService.update(param,user);
        return new Result(user.getUsername(),"修改拼团活动", GsonUtil.objectToGson(param));
    }

    /**
     * 删除拼团活动
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除拼团活动")
    @NoRepeatWebLog
    public Result delete(@RequestBody ShopGroupWorkGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopGroupWorkService.delete(param.getShopGroupWorkId(),user);
        return new Result(user.getUsername(),"删除拼团活动", GsonUtil.objectToGson(param));
    }

    /**
     * 停止拼团活动
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止拼团活动")
    @NoRepeatWebLog
    public Result stop(@RequestBody ShopGroupWorkGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopGroupWorkService.stop(param.getShopGroupWorkId(),user);
        return new Result(user.getUsername(),"停止拼团活动", GsonUtil.objectToGson(param));
    }

    /**
     * 拼团活动详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "拼团活动详情查询")
    public Result<ShopGroupWorkUDetail> getById(@RequestBody ShopGroupWorkGetByIdParam param) throws CoBusinessException{
        ShopGroupWorkUDetail detail=cereShopGroupWorkService.getById(param.getShopGroupWorkId());
        return new Result(detail);
    }

    /**
     * 数据效果查询
     * @return
     */
    @RequestMapping(value = "getData",method = RequestMethod.POST)
    @ApiOperation(value = "数据效果查询")
    public Result<ShopGroupWorkData> getData(@RequestBody ShopGroupWorkGetByIdParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        ShopGroupWorkData detail=cereShopGroupWorkService.getData(param,user.getShopId());
        return new Result(detail);
    }

    /**
     * 拼团活动管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "拼团活动管理查询")
    public Result<Page<ShopGroupWork>> getAll(@RequestBody ShopGroupWorkGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopGroupWorkService.getAll(param);
        return new Result(page);
    }

    /**
     * 选择商品查询
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "选择商品查询")
    public Result<Page<ToolProduct>> getProducts(@RequestBody ToolProductParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page=cereShopGroupWorkService.getProducts(param);
        return new Result(page);
    }
}
