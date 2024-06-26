/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.page.collect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 足迹商品数据
 */
@Data
@ApiModel(value = "FootprintProduct", description = "足迹商品数据")
public class FootprintProduct {

    /**
     * 足迹id
     */
    @ApiModelProperty(value = "足迹id")
    private Long footprintId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 规格id
     */
    @ApiModelProperty(value = "规格id")
    private Long skuId;

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
     * 商品售价
     */
    @ApiModelProperty(value = "商品售价")
    private BigDecimal price;

    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @ApiModelProperty(value = "是否上架 1-上架 0-不上架")
    private Integer shelveState;

    /**
     * 是否选中 1-是 0-否
     */
    @ApiModelProperty(value = "是否选中 1-是 0-否")
    private Integer selected;

    /**
     * show
     */
    private boolean show=false;
}
