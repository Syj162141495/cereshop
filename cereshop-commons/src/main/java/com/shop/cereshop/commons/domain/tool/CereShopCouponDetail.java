/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_coupon_detail 店铺优惠券商品明细实体
 * @author 
 */
@Data
public class CereShopCouponDetail implements Serializable {
    /**
     * 店铺优惠券id
     */
    @ApiModelProperty(value = "店铺优惠券id")
    private Long shopCouponId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    private static final long serialVersionUID = 1L;

}
