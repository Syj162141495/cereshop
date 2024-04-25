/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.seckill.*;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.admin.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
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
 * 平台秒杀活动管理
 */
@RestController
@RequestMapping("seckill")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "SeckillController")
@Api(value = "平台秒杀活动管理模块", tags = "平台秒杀活动管理模块")
public class SeckillController {

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    /**
     * 新增平台秒杀活动
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "新增平台秒杀活动")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated SeckillSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSeckillService.save(param,user);
        return new Result(user.getUsername(),"新增平台秒杀活动", GsonUtil.objectToGson(param));
    }

    /**
     * 修改平台秒杀活动
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改平台秒杀活动")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated SeckillUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSeckillService.update(param,user);
        return new Result(user.getUsername(),"修改平台秒杀活动", GsonUtil.objectToGson(param));
    }

    /**
     * 删除平台秒杀活动
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除平台秒杀活动")
    @NoRepeatWebLog
    public Result delete(@RequestBody SeckillGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSeckillService.delete(param.getSeckillId(),user);
        return new Result(user.getUsername(),"删除平台秒杀活动", GsonUtil.objectToGson(param));
    }

    /**
     * 停止平台秒杀活动
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止平台秒杀活动")
    @NoRepeatWebLog
    public Result stop(@RequestBody SeckillGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformSeckillService.stop(param.getSeckillId(),user);
        return new Result(user.getUsername(),"停止平台秒杀活动", GsonUtil.objectToGson(param));
    }

    /**
     * 平台秒杀详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "平台秒杀详情查询查询")
    public Result<CerePlatformSeckill> getById(@RequestBody SeckillGetByIdParam param) throws CoBusinessException{
        CerePlatformSeckill detail=cerePlatformSeckillService.getById(param.getSeckillId());
        return new Result(detail);
    }

    /**
     * 数据效果查询
     * @return
     */
    @RequestMapping(value = "getData",method = RequestMethod.POST)
    @ApiOperation(value = "数据效果查询")
    public Result<SeckillData> getData(@RequestBody SeckillGetByIdParam param) throws CoBusinessException{
        SeckillData detail=cerePlatformSeckillService.getData(param);
        return new Result(detail);
    }

    /**
     * 平台秒杀活动管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "平台秒杀活动管理查询")
    public Result<Page<Seckill>> getAll(@RequestBody SeckillGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        Page page =cerePlatformSeckillService.getAll(param);
        return new Result(page);
    }

    /**
     * 参与店铺查询
     * @return
     */
    @RequestMapping(value = "getShops",method = RequestMethod.POST)
    @ApiOperation(value = "参与店铺查询")
    public Result<Page<SeckillShop>> getShops(@RequestBody SeckillShopParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        Page page=cerePlatformSeckillService.getShops(param);
        return new Result(page);
    }

    /**
     * 查看商品
     * @param param
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "查看商品")
    public Result<Page<SeckillShopProduct>> getProducts(@RequestBody SeckillGetProductsParam param) throws CoBusinessException{
        Page page=cerePlatformSeckillService.getProducts(param);
        return new Result(page);
    }

    /**
     * 商家数据明细导出导出
     * @param param
     * @param response
     */
    @RequestMapping(value = "excel_shop_detail",method = RequestMethod.POST)
    @ApiOperation(value = "商家数据明细导出导出")
    public void excelShop(@RequestBody SeckillGetByIdParam param, HttpServletResponse response) throws CoBusinessException,Exception{
        List<ShopDataDetail> list=cerePlatformSeckillService.findExcel(param);
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
