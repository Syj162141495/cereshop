/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品规格明细参数
 */
@Data
@ApiModel(value = "SkuParam", description = "商品规格明细参数")
public class SkuParam {

    /**
     * 规格id
     */
    @ApiModelProperty(value = "规格id")
    private Long skuId;

    /**
     * 规格名和值数组
     */
    @ApiModelProperty(value = "规格名数组")
    private List<SkuNameValueParam> skuAttrCodeDTOList;

    /**
     * SKU
     */
    @ApiModelProperty(value = "SKU")
    private String sku;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    @NotNull(message = "售价不能为空")
    private BigDecimal price;

    /**
     * 原价
     */
    @ApiModelProperty(value = "原价")
    @NotNull(message = "原价不能为空")
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    @NotNull(message = "库存不能为空")
    private Integer stockNumber;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    @NotNull(message = "重量不能为空")
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
     * 导入数据
     */
    private NameValue nameValue;
}
