/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 规格属性值数据
 */
@Data
@ApiModel(value = "SkuValue", description = "规格属性值数据")
public class SkuValue implements Serializable {

    private static final long serialVersionUID = 1085591397832468816L;
    /**
     * 规格名
     */
    @ApiModelProperty(value = "规格名")
    private String skuName;

    /**
     * 规格值
     */
    @ApiModelProperty(value = "规格值")
    private String skuValue;

    /**
     * 规格值级别
     */
    @ApiModelProperty(value = "规格值级别")
    private String valueCode;
}
