/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取商品详情请求
 */
@Data
@ApiModel(value = "ProductGetByIdParam", description = "获取商品详情请求")
public class ProductGetByIdParam {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;
}
