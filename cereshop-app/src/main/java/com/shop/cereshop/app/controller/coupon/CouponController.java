/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.controller.coupon;

import com.shop.cereshop.app.annotation.NoRepeatWebLog;
import com.shop.cereshop.app.page.index.Product;
import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.app.param.coupon.ActivityParam;
import com.shop.cereshop.app.param.coupon.CouponListParam;
import com.shop.cereshop.app.param.coupon.CouponParam;
import com.shop.cereshop.app.param.coupon.OrderCouponParam;
import com.shop.cereshop.app.service.activity.CereBuyerCouponService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
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
 * 卡券
 */
@RestController
@RequestMapping("coupon")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "CouponController")
@Api(value = "卡券", tags = "卡券")
public class CouponController {

    @Autowired
    private CereBuyerCouponService cereBuyerCouponService;

    /**
     * 领取优惠券
     * @param param
     * @return
     */
    @PostMapping("takeCoupon")
    @ApiOperation(value = "领取优惠券")
    @NoRepeatWebLog
    public Result takeCoupon(@RequestBody CouponParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerCouponService.takeCoupon(param,user);
        return new Result(CoReturnFormat.SUCCESS,user.getWechatName(),"领取优惠券", GsonUtil.objectToGson(param));
    }

    /**
     * 我的卡券列表
     * @return
     */
    @GetMapping("getCoupons")
    @ApiOperation(value = "我的卡券列表")
    public Result<List<ProductCoupon>> getPageCoupons(HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        List<ProductCoupon> list=cereBuyerCouponService.getCoupons(user.getBuyerUserId());
        return new Result(list,CoReturnFormat.SUCCESS);
    }

    /**
     * 我的卡券列表（不带分页）
     * @param param
     * @return
     */
    @GetMapping("getCouponList")
    @ApiOperation(value = "我的卡券列表")
    public Result<List<ProductCoupon>> getCoupons(CouponListParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        List<ProductCoupon> list=cereBuyerCouponService.getCouponList(param,user);
        return new Result(list,CoReturnFormat.SUCCESS);
    }

    /**
     * 结算页优惠券列表查询
     * @param param
     * @return
     */
    @GetMapping("getOrderCoupons")
    @ApiOperation(value = "结算页优惠券列表查询")
    public Result<List<ProductCoupon>> getOrderCoupons(OrderCouponParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        List<ProductCoupon> list=cereBuyerCouponService.getOrderCoupons(param,user);
        return new Result(list,CoReturnFormat.SUCCESS);
    }

    /**
     * 优惠券商品列表查询
     * @return
     */
    @GetMapping("getCouponProducts")
    @ApiOperation(value = "优惠券商品列表查询")
    public Result<Page<Product>> getCouponProducts(ActivityParam param) throws CoBusinessException{
        Page page= cereBuyerCouponService.getCouponProducts(param);
        return new Result(page,CoReturnFormat.SUCCESS);
    }
}
