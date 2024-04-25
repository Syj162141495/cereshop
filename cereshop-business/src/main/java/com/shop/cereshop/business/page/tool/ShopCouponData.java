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
import java.math.BigDecimal;
import java.util.List;

/**
 * 满减券/折扣券数据效果
 */
@Data
@ApiModel(value = "ShopCouponData", description = "满减券/折扣券数据效果返回数据")
public class ShopCouponData implements Serializable {
    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    /**
     * 用券成交总额
     */
    @ApiModelProperty(value = "用券成交总额")
    private BigDecimal total;

    /**
     * 使用优惠券总额
     */
    @ApiModelProperty(value = "使用优惠券总额")
    private BigDecimal useMoney;

    /**
     * 购买商品总数
     */
    @ApiModelProperty(value = "购买商品总数")
    private Integer count;

    /**
     * 使用优惠券购买的商品明细数据
     */
    @ApiModelProperty(value = "使用优惠券购买的商品明细数据")
    private List<CouponProduct> products;

    /**
     * 优惠券总数
     */
    @ApiModelProperty(value = "优惠券总数")
    private Integer couponTotal;

    /**
     * 已领取数
     */
    @ApiModelProperty(value = "已领取数")
    private Integer received;

    private static final long serialVersionUID = 1L;

}
