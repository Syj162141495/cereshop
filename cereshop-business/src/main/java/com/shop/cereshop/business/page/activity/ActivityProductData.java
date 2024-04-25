/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 营销活动商品明细数据
 */
@Data
@ApiModel(value = "ActivityProductData", description = "营销活动商品明细数据")
public class ActivityProductData {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String image;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 商品最低售价
     */
    private BigDecimal minPrice;

    /**
     * 商品最高售价
     */
    private BigDecimal maxPrice;

    /**
     * 优惠类型 1-满减 2-折扣
     */
    private Integer discountMode;

    /**
     * 优惠券金额/折扣
     */
    private BigDecimal couponContent;

    /**
     * 活动价区间
     */
    @ApiModelProperty(value = "活动价区间")
    private String sectionPrice;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Integer stockNumber;

    /**
     * 活动销量
     */
    @ApiModelProperty(value = "活动销量")
    private Integer valume;

    /**
     * 活动成交金额
     */
    @ApiModelProperty(value = "活动成交金额")
    private BigDecimal total;

    /**
     * 商品限量
     */
    @ApiModelProperty(value = "商品限量")
    private Integer number;
}
