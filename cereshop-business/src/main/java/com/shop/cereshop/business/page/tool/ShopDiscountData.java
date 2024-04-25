/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.tool;

import com.shop.cereshop.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 限时折扣活动效果
 */
@Data
@ApiModel(value = "ShopDiscountData", description = "限时折扣活动效果返回数据")
public class ShopDiscountData implements Serializable {
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String discountName;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Integer visit;

    /**
     * 付款订单数
     */
    @ApiModelProperty(value = "付款订单数")
    private Integer pays;

    /**
     * 平均每小时付款订单数
     */
    @ApiModelProperty(value = "平均每小时付款订单数")
    private Integer hourPays;

    /**
     * 活动成交金额
     */
    @ApiModelProperty(value = "活动成交金额")
    private BigDecimal total;

    /**
     * 使用优惠券购买的商品明细数据
     */
    @ApiModelProperty(value = "使用优惠券购买的商品明细数据")
    private Page<CouponProduct> products;
    private static final long serialVersionUID = 1L;

}
