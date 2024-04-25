/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设置虚拟销量请求
 */
@Data
@ApiModel(value = "ProductUpdateParam", description = "设置虚拟销量请求")
public class ProductUpdateParam {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 虚拟销量
     */
    @ApiModelProperty(value = "虚拟销量")
    private Integer fictitiousNumber;

}
