/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.canvas.*;
import com.shop.cereshop.business.page.price.ShopPriceDetail;
import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.service.activity.CerePlatformActivityService;
import com.shop.cereshop.business.service.canvas.CerePlatformCanvasService;
import com.shop.cereshop.business.service.price.CerePriceProductService;
import com.shop.cereshop.business.service.price.CereShopPriceService;
import com.shop.cereshop.business.service.product.CereProductClassifyService;
import com.shop.cereshop.business.service.product.CereShopProductService;
import com.shop.cereshop.business.service.tool.CereShopCouponService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.commons.domain.tool.CereShopPrice;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 画布查询
 */
@RestController
@RequestMapping("canvas")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "CanvasController")
@Api(value = "画布控制器", tags = "画布控制器")
public class CanvasController {

    @Autowired
    private CereShopProductService cereShopProductService;

    @Autowired
    private CereProductClassifyService cereProductClassifyService;

    @Autowired
    private CerePlatformCanvasService cerePlatformCanvasService;

    @Autowired
    private CerePlatformActivityService cerePlatformActivityService;

    @Autowired
    private CereShopCouponService cereShopCouponService;

    @Autowired
    private CerePriceProductService cerePriceProductService;

    @Autowired
    private CereShopPriceService cereShopPriceService;

    /**
     * 画布选择商品查询
     * @param param
     * @return
     */
    @GetMapping("getProducts")
    @ApiOperation(value = "商家编辑查询")
    public Result<Page<CanvasProduct>> getProducts(CanvasProductParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopProductService.getProducts(param);
        return new Result(page);
    }

    /**
     * 画布选择定价捆绑活动数据
     * @param param
     * @return
     */
    @GetMapping("getPrices")
    @ApiOperation(value = "画布选择定价捆绑活动数据")
    public Result<List<ShopPriceDetail>> getPrices(RenovationParam param) throws CoBusinessException{
        List<ShopPriceDetail> list=cereShopPriceService.getPrices(param);
        return new Result(list);
    }

    /**
     * 画布选择店铺查询
     * @param param
     * @return
     */
    @GetMapping("getShops")
    @ApiOperation(value = "画布选择店铺查询")
    public Result<Page<CanvasShop>> getShops(CanvasProductParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopProductService.getShops(param);
        return new Result(page);
    }

    /**
     * 查询分类层级
     * @return
     */
    @GetMapping("getClassify")
    @ApiOperation(value = "查询分类层级")
    public Result<Classify> getClassify() throws CoBusinessException{
        List<Classify> list=cereProductClassifyService.getClassify();
        return new Result(list);
    }

    /**
     * 查询所有平台优惠券
     * @return
     */
    @GetMapping("getCoupons")
    @ApiOperation(value = "查询所有平台优惠券")
    public Result<Page<CanvasCoupon>> getCoupons(CanvasCouponParam param) throws CoBusinessException {
        param.setShopId(ContextUtil.getShopId());
        Page page=cerePlatformActivityService.getCoupons(param);
        return new Result(page);
    }

    /**
     * 查询所有店铺优惠券
     * @return
     */
    @GetMapping("getShopCoupons")
    @ApiOperation(value = "查询所有店铺优惠券")
    public Result<Page<ProductCoupon>> getShopCoupons(CanvasCouponParam param) throws CoBusinessException {
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopCouponService.getShopCoupons(param);
        return new Result(page);
    }

    /**
     * 保存画布
     * @return
     */
    @PostMapping("saveCanvas")
    @ApiOperation(value = "保存画布")
    @NoRepeatWebLog
    public Result saveCanvas(@RequestBody CerePlatformCanvas canvas, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        canvas.setShopId(ContextUtil.getShopId());
        cerePlatformCanvasService.saveCanvas(canvas,user);
        return new Result(user.getUsername(),"保存画布", GsonUtil.objectToGson(canvas));
    }

    /**
     * 读取画布数据
     * @return
     */
    @GetMapping("getCanvas")
    @ApiOperation(value = "读取画布数据")
    public Result getCanvas(CerePlatformCanvas canvas) throws CoBusinessException{
        canvas.setShopId(ContextUtil.getShopId());
        canvas=cerePlatformCanvasService.getCanvas(canvas);
        return new Result(canvas);
    }

}
