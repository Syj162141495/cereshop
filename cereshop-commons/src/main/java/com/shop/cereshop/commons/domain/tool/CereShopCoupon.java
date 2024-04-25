/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_coupon  店铺优惠券实体
 * @author 
 */
@Data
public class CereShopCoupon implements Serializable {
    /**
     * 店铺优惠券id
     */
    @ApiModelProperty(value = "店铺优惠券id")
    private Long shopCouponId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "备注")
    private String couponName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 优惠券类型 1-满减券 2-折扣券
     */
    @ApiModelProperty(value = "优惠券类型 1-满减券 2-折扣券")
    private Integer couponType;

    /**
     * 适用商品 1-全部商品 2-指定商品可用 3-指定商品不可用
     */
    @ApiModelProperty(value = "适用商品 1-全部商品 2-指定商品可用 3-指定商品不可用")
    private Integer applyType;

    /**
     * 使用门槛满多少元，无门槛为0
     */
    @ApiModelProperty(value = "使用门槛满多少元，无门槛为0")
    private BigDecimal threshold;

    /**
     * 优惠内容减多少元
     */
    @ApiModelProperty(value = "优惠内容减多少元")
    private BigDecimal couponContent;

    /**
     * 用券时间 1-固定时间 2-领券当日起几天内可用
     */
    @ApiModelProperty(value = "用券时间 1-固定时间 2-领券当日起几天内可用")
    private Integer timeType;

    /**
     * 用券开始时间
     */
    @ApiModelProperty(value = "用券开始时间")
    private String effectiveStart;

    /**
     * 用券结束时间
     */
    @ApiModelProperty(value = "用券结束时间")
    private String effectiveEnd;

    /**
     * 领券当日几天内（天数）
     */
    @ApiModelProperty(value = "领券当日几天内（天数）")
    private Integer effectiveDay;

    /**
     * 发放数量
     */
    @ApiModelProperty(value = "发放数量")
    private Integer number;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer stockNumber;

    /**
     * 每人限领次数  1-无限次 2-限制几次
     */
    @ApiModelProperty(value = "每人限领次数  1-无限次 2-限制几次")
    private Integer receiveType;

    /**
     * 限制次数
     */
    @ApiModelProperty(value = "限制次数")
    private Integer frequency;

    /**
     * 优惠券状态  0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "优惠券状态  0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 是否叠加平台优惠 1-是 0-否
     */
    @ApiModelProperty(value = "是否叠加平台优惠 1-是 0-否")
    private Integer ifAdd;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
