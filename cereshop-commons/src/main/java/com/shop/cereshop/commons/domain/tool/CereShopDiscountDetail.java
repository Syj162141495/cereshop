/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_discount_detail 店铺限时折扣活动商品明细实体
 * @author 
 */
@Data
public class CereShopDiscountDetail implements Serializable {
    /**
     * 店铺限时折扣活动id
     */
    @ApiModelProperty(value = "店铺限时折扣活动id")
    private Long shopDiscountId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    /**
     * 规格id
     */
    @ApiModelProperty(value = "规格id",required = true)
    private Long skuId;

    /**
     * 折扣
     */
    @ApiModelProperty(value = "折扣",required = true)
    @NotNull(message = "折扣不能为空")
    private BigDecimal discount;

    /**
     * 活动价格
     */
    @ApiModelProperty(value = "活动价格",required = true)
    @NotNull(message = "活动价格不能为空")
    private BigDecimal price;

    /**
     * 限量数量
     */
    private Integer number;

    /**
     * 限量总数
     */
    private Integer total;

    private static final long serialVersionUID = 1L;
}
