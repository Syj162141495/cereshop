/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品返回数据实体类
 */
@Data
@ApiModel(value = "Product", description = "订单商品返回数据实体类")
public class Product {

    /**
     * 售后商品明细id
     */
    @ApiModelProperty(value = "售后商品明细id")
    private Long afterProductId;

    /**
     * 订单商品明细id
     */
    @ApiModelProperty(value = "订单商品明细id")
    private Long orderProductId;

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
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 规格名称
     */
    @ApiModelProperty(value = "规格名称")
    private String skuName;

    /**
     * 规格值
     */
    @ApiModelProperty(value = "规格值")
    private String skuValue;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * SKU
     */
    @ApiModelProperty(value = "SKU")
    private String SKU;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer number;

    /**
     * 售价区间
     */
    @ApiModelProperty(value = "售价区间")
    private String sectionPrice;

    /**
     * 商品总价
     */
    @ApiModelProperty(value = "商品总价")
    private BigDecimal total;

    /**
     * 最低价格
     */
    @ApiModelProperty(value = "最低价格")
    private BigDecimal minMoney;

    /**
     * 最高价格
     */
    @ApiModelProperty(value = "最高价格")
    private BigDecimal maxMoney;

    /**
     * 规格属性值
     */
    @ApiModelProperty(value = "规格属性值")
    private String value;

    /**
     * 规格属性明细数据
     */
    @ApiModelProperty(value = "规格属性明细数据")
    private List<SkuDetail> skuDetails;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer stockNumber;
}
