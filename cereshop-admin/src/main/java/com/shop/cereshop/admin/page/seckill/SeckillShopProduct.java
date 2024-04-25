/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.seckill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品返回数据实体类
 */
@Data
@ApiModel(value = "SeckillShopProduct", description = "商品返回数据实体类")
public class SeckillShopProduct {

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
     * 秒杀价区间
     */
    @ApiModelProperty(value = "秒杀价区间")
    private String sectionPrice;

    /**
     * 原价区间
     */
    @ApiModelProperty(value = "原价区间")
    private String originalPrice;

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
     * 限量 0-不限量
     */
    @ApiModelProperty(value = "限量 0-不限量")
    private Integer number;
}
