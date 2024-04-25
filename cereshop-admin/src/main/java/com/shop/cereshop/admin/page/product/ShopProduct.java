/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品返回数据实体类
 */
@Data
@ApiModel(value = "ShopProduct", description = "商品返回数据实体类")
public class ShopProduct {

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
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 售价区间
     */
    @ApiModelProperty(value = "售价区间")
    private String sectionPrice;

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
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Integer stockNumber;

    /**
     * 实际销量
     */
    @ApiModelProperty(value = "实际销量")
    private Integer volume;

    /**
     * 虚拟销量
     */
    @ApiModelProperty(value = "虚拟销量")
    private Integer fictitiousNumber;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 商品状态 0-已下架 1-已上架 2-待审核 3-审核失败
     */
    @ApiModelProperty(value = "商品状态 0-已下架 1-已上架 2-待审核 3-审核失败")
    private Integer shelveState;

}
