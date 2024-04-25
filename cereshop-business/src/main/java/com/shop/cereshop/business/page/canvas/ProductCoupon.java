/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 优惠券数据
 */
@Data
@ApiModel(value = "ProductCoupon", description = "优惠券数据")
public class ProductCoupon {
    /**
     * 店铺优惠券id
     */
    @ApiModelProperty(value = "店铺优惠券id")
    private Long shopCouponId;

    /**
     * 店铺优惠券类型 1-满减券 2-折扣券
     */
    @ApiModelProperty(value = "店铺优惠券类型 1-满减券 2-折扣券")
    private Integer couponType;

    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private String effectiveStart;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private String effectiveEnd;

    /**
     * 满多少元
     */
    @ApiModelProperty(value = "满多少元")
    private BigDecimal threshold;

    /**
     * 减多少元
     */
    @ApiModelProperty(value = "减多少元")
    private BigDecimal couponContent;

    /**
     * 优惠描述(无门槛使用或者满多少元使用)
     */
    @ApiModelProperty(value = "优惠描述(无门槛使用或者满多少元使用)")
    private String content;
}
