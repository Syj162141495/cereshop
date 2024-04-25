/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 使用优惠券购买的商品明细数据
 */
@Data
@ApiModel(value = "CouponProduct", description = "使用优惠券购买的商品明细数据")
public class CouponProduct implements Serializable {
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 付款件数
     */
    @ApiModelProperty(value = "付款件数")
    private Integer number;

    /**
     * 付款人数
     */
    @ApiModelProperty(value = "付款人数")
    private Integer users;

    private static final long serialVersionUID = 1L;

}
