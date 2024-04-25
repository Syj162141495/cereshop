/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品会员价规格数据
 * @author
 */
@Data
@ApiModel(value = "ProductMember", description = "商品会员价规格数据")
public class ProductMember {

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
     * 规格值
     */
    @ApiModelProperty(value = "规格值")
    private String value;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    private BigDecimal price;

    /**
     * 优惠方式   1-折扣 2-指定价格
     */
    @ApiModelProperty(value = "优惠方式   1-折扣 2-指定价格")
    private Integer mode;

    /**
     * 会员价格数据明细
     */
    @ApiModelProperty(value = "会员价格数据明细")
    private List<MemberPrice> memberPrices;
}
