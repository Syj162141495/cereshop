/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_product_sku 商品规格信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereProductSku", description = "商品规格信息实体类")
public class CereProductSku implements Serializable {

    /**
     * 商品规格id
     */
    @ApiModelProperty(value = "商品规格id")
    private Long skuId;

    /**
     * 关联商品id
     */
    @ApiModelProperty(value = "关联商品id")
    private Long productId;

    /**
     * SKU
     */
    @ApiModelProperty(value = "SKU")
    private String SKU;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    private BigDecimal price;

    /**
     * 原价
     */
    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer stockNumber;

    /**
     * 库存总数
     */
    @ApiModelProperty(value = "库存总数")
    private Integer total;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private BigDecimal weight;

    /**
     * 规格图片地址
     */
    @ApiModelProperty(value = "规格图片地址")
    private String skuImage;

    /**
     * 款式  0-单款式 1-多款式
     */
    @ApiModelProperty(value = "款式  0-单款式 1-多款式")
    private Integer style;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
