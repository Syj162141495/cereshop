/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 营销活动商品数据
 */
@Data
@ApiModel(value = "ToolProduct", description = "营销活动商品数据实体类")
public class ToolProduct {

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
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 活动价格
     */
    @ApiModelProperty(value = "活动价格")
    private BigDecimal price;

    /**
     * 限时折扣/直降多少元
     */
    @ApiModelProperty(value = "限时折扣")
    private BigDecimal discount;

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
     * 规格值
     */
    @ApiModelProperty(value = "规格值")
    private String value;

    /**
     * 已拼人数
     */
    @ApiModelProperty(value = "已拼人数")
    private Integer workUsers;
}
