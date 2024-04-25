/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.activity.*;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.param.activity.*;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.ShopDetail;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.poi.ExcelUtils;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 营销活动
 */
@RestController
@RequestMapping("activity")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ActivityController")
@Api(value = "营销活动模块", tags = "营销活动模块")
public class ActivityController {

    @Autowired
    private CerePlatformActivityService cerePlatformActivityService;

    /**
     * 添加平台优惠券
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加平台优惠券")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated ActivitySaveParam param,
                       HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformActivityService.save(param,user);
        return new Result(user.getUsername(),"添加平台优惠券", GsonUtil.objectToGson(param));
    }

    /**
     * 修改平台优惠券
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改平台优惠券")
    @NoRepeatWebLog
    public Result update(@RequestBody ActivityUpdateParam param,
                         HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformActivityService.update(param,user);
        return new Result(user.getUsername(),"修改平台优惠券", GsonUtil.objectToGson(param));
    }

    /**
     * 删除平台优惠券
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除平台优惠券")
    @NoRepeatWebLog
    public Result delete(@RequestBody ActivityDelteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformActivityService.delete(param,user);
        return new Result(user.getUsername(),"删除平台优惠券", GsonUtil.objectToGson(param));
    }

    /**
     * 结束平台优惠券活动
     * @param param
     * @return
     */
    @RequestMapping(value = "end",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "结束平台优惠券活动")
    @NoRepeatWebLog
    public Result end(@RequestBody ActivityEndParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformActivityService.end(param,user);
        return new Result(user.getUsername(),"结束平台优惠券活动", GsonUtil.objectToGson(param));
    }

    /**
     * 平台优惠券活动详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "平台优惠券活动详情查询")
    public Result<ActivityDetail> getById(@RequestBody ActivityGetByIdParam param) throws CoBusinessException{
        ActivityDetail activity=cerePlatformActivityService.getById(param.getActivityId());
        return new Result(activity);
    }

    /**
     * 平台优惠券活动管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "平台优惠券活动管理查询")
    public Result<Page<Activity>> getAll(@RequestBody ActivityGetAllParam param) throws CoBusinessException{
        Page page=cerePlatformActivityService.getAll(param);
        return new Result(page);
    }

    /**
     * 参与店铺数据管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getShops",method = RequestMethod.POST)
    @ApiOperation(value = "参与店铺数据管理查询")
    public Result<Page<ShopActivity>> getShops(@RequestBody ActivityGetShopsParam param) throws CoBusinessException{
        Page page=cerePlatformActivityService.getShops(param);
        return new Result(page);
    }

    /**
     * 活动数据查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getActivitys",method = RequestMethod.POST)
    @ApiOperation(value = "活动数据查询")
    public Result<ActivityData> getActivitys(@RequestBody ActivityGetDatasParam param) throws CoBusinessException{
        ActivityData data=cerePlatformActivityService.getActivitys(param);
        return new Result(data);
    }

    /**
     * 活动数据查询
     * @param param
     * @return
     */
//    @RequestMapping(value = "getActivitys",method = RequestMethod.POST)
//    @ApiOperation(value = "活动数据查询")
//    public Result<ActivityData> getActivitys(@RequestBody ActivityGetDatasParam param) throws CoBusinessException{
//        ActivityData data=cerePlatformActivityService.getTest(param);
//        return new Result(data);
//    }

    /**
     * 商家数据明细导出导出
     * @param param
     * @param response
     */
    @RequestMapping(value = "excel_shop_detail",method = RequestMethod.POST)
    @ApiOperation(value = "商家数据明细导出导出")
    public void excelShop(@RequestBody ActivityGetDatasParam param, HttpServletResponse response) throws CoBusinessException,Exception{
        List<ShopDetail> list=cerePlatformActivityService.findExcel(param);
        List<String> titles=new ArrayList<>();
        titles.add("店铺名称");
        titles.add("店铺编码");
        titles.add("参与商品数");
        titles.add("访客数");
        titles.add("订单数");
        titles.add("成交客户数");
        titles.add("客单价");
        titles.add("成交总额");
        XSSFWorkbook excel = ExcelUtils.createShopDetailExcel(titles, list);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "订单管理表"+sdf.format(date);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + str +".xls");// 默认Excel名称
        response.flushBuffer();
        excel.write(response.getOutputStream());
        excel.close();
    }

    /**
     * 查看商品
     * @param param
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "查看商品")
    public Result<Page<ShopProduct>> getProducts(@RequestBody ActivityGetProductsParam param) throws CoBusinessException{
        Page page=cerePlatformActivityService.getProducts(param);
        return new Result(page);
    }

    /**
     * 审核记录查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getExamines",method = RequestMethod.POST)
    @ApiOperation(value = "审核记录查询")
    public Result<List<SignExamineLog>> getExamines(@RequestBody ActivityGetExaminesParam param) throws CoBusinessException{
        List<SignExamineLog> page=cerePlatformActivityService.getExamines(param);
        return new Result(page);
    }

    /**
     * 保证金交易记录查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAdminBonds",method = RequestMethod.POST)
    @ApiOperation(value = "保证金交易记录查询")
    public Result<BondCount> getAdminBonds(@RequestBody ActivityGetAdminBondsParam param) throws CoBusinessException{
        BondCount count=cerePlatformActivityService.getAdminBonds(param);
        return new Result(count);
    }

    /**
     * 审核商家报名
     * @param param
     * @return
     */
    @RequestMapping(value = "examine",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "审核")
    @NoRepeatWebLog
    public Result examine(@RequestBody ActivityExamineParam param,
                          HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformActivityService.examine(param,user);
        return new Result(user,"审核商家报名");
    }

    /**
     * 清退
     * @param param
     * @return
     */
    @RequestMapping(value = "liquidation",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "清退")
    @NoRepeatWebLog
    public Result liquidation(@RequestBody ActivityLiquidationParam param,
                              HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformActivityService.liquidation(param,user);
        return new Result(user.getUsername(),"清退", GsonUtil.objectToGson(param));
    }
}
