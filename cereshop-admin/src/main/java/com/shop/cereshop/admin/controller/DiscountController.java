/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.discount.Discount;
import com.shop.cereshop.admin.page.discount.DiscountData;
import com.shop.cereshop.admin.page.discount.DiscountShop;
import com.shop.cereshop.admin.page.discount.DiscountShopProduct;
import com.shop.cereshop.admin.page.seckill.Seckill;
import com.shop.cereshop.admin.page.seckill.SeckillData;
import com.shop.cereshop.admin.page.seckill.SeckillShop;
import com.shop.cereshop.admin.page.seckill.SeckillShopProduct;
import com.shop.cereshop.admin.param.discount.*;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.admin.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
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
 * 平台限时折扣活动管理
 */
@RestController
@RequestMapping("discount")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "DiscountController")
@Api(value = "平台限时折扣活动管理模块", tags = "平台限时折扣活动管理模块")
public class DiscountController {

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    /**
     * 新增平台限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增平台限时折扣活动")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated DiscountSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDiscountService.save(param,user);
        return new Result(user.getUsername(),"新增平台限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 修改平台限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改平台限时折扣活动")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated DiscountUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDiscountService.update(param,user);
        return new Result(user.getUsername(),"修改平台限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 删除平台限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除平台限时折扣活动")
    @NoRepeatWebLog
    public Result delete(@RequestBody DiscountGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDiscountService.delete(param.getDiscountId(),user);
        return new Result(user.getUsername(),"删除平台限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 停止平台限时折扣活动
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止平台限时折扣活动")
    @NoRepeatWebLog
    public Result stop(@RequestBody DiscountGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDiscountService.stop(param.getDiscountId(),user);
        return new Result(user.getUsername(),"停止平台限时折扣活动", GsonUtil.objectToGson(param));
    }

    /**
     * 平台限时折扣详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "平台限时折扣详情查询")
    public Result<CerePlatformDiscount> getById(@RequestBody DiscountGetByIdParam param) throws CoBusinessException{
        CerePlatformDiscount detail=cerePlatformDiscountService.getById(param.getDiscountId());
        return new Result(detail);
    }

    /**
     * 数据效果查询
     * @return
     */
    @RequestMapping(value = "getData",method = RequestMethod.POST)
    @ApiOperation(value = "数据效果查询")
    public Result<DiscountData> getData(@RequestBody DiscountGetByIdParam param) throws CoBusinessException{
        DiscountData detail=cerePlatformDiscountService.getData(param);
        return new Result(detail);
    }

    /**
     * 平台限时折扣活动管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "平台限时折扣活动管理查询")
    public Result<Page<Discount>> getAll(@RequestBody DiscountGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        Page page =cerePlatformDiscountService.getAll(param);
        return new Result(page);
    }

    /**
     * 参与店铺查询
     * @return
     */
    @RequestMapping(value = "getShops",method = RequestMethod.POST)
    @ApiOperation(value = "参与店铺查询")
    public Result<Page<DiscountShop>> getShops(@RequestBody DiscountShopParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        Page page=cerePlatformDiscountService.getShops(param);
        return new Result(page);
    }

    /**
     * 查看商品
     * @param param
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "查看商品")
    public Result<Page<DiscountShopProduct>> getProducts(@RequestBody DiscountGetProductsParam param) throws CoBusinessException{
        Page page=cerePlatformDiscountService.getProducts(param);
        return new Result(page);
    }

    /**
     * 商家数据明细导出导出
     * @param param
     * @param response
     */
    @RequestMapping(value = "excel_shop_detail",method = RequestMethod.POST)
    @ApiOperation(value = "商家数据明细导出导出")
    public void excelShop(@RequestBody DiscountGetByIdParam param, HttpServletResponse response) throws CoBusinessException,Exception{
        List<ShopDataDetail> list=cerePlatformDiscountService.findExcel(param);
        List<String> titles=new ArrayList<>();
        titles.add("商家名称");
        titles.add("店铺编码");
        titles.add("参与商品数");
        titles.add("访客数");
        titles.add("提交订单笔数");
        titles.add("成交笔数");
        titles.add("成交总额");
        XSSFWorkbook excel = ExcelUtils.createSeckillShopDetailExcel(titles, list);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "订单管理表"+sdf.format(date);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + str +".xls");// 默认Excel名称
        response.flushBuffer();
        excel.write(response.getOutputStream());
        excel.close();
    }
}
