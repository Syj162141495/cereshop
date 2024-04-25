/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 报名商品明细数据
 */
@Data
@ApiModel(value = "SignProductParam", description = "报名商品明细数据")
public class SignProductParam {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 商品限量
     */
    @ApiModelProperty(value = "商品限量")
    private Integer number;
}
