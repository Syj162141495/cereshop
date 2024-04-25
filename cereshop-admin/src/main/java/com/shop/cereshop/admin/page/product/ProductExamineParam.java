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
 * 审核商品请求
 */
@Data
@ApiModel(value = "ProductUpdateParam", description = "审核商品请求")
public class ProductExamineParam {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 商品状态 1-通过 3-驳回
     */
    @ApiModelProperty(value = "商品状态 1-通过 3-驳回")
    private Integer shelveState;

    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    private String reject;
}
