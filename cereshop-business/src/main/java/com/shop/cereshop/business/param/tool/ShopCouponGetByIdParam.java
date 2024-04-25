/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改店铺满减券/折扣券
 */
@Data
@ApiModel(value = "ShopCouponGetByIdParam", description = "修改、删除、停止店铺满减券/折扣券请求")
public class ShopCouponGetByIdParam implements Serializable {
    /**
     * 店铺优惠券id
     */
    @ApiModelProperty(value = "店铺优惠券id")
    private Long shopCouponId;

    private static final long serialVersionUID = 1L;

}
