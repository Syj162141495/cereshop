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
import java.util.List;

/**
 * 规格属性名数据
 */
@Data
@ApiModel(value = "SkuName", description = "规格属性名数据")
public class SkuName implements Serializable {

    private static final long serialVersionUID = 7834859279609115324L;
    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 规格名
     */
    @ApiModelProperty(value = "规格名")
    private String skuName;

    /**
     * 规格名级别
     */
    @ApiModelProperty(value = "规格名级别")
    private String nameCode;

    /**
     * 规格值属性数组
     */
    @ApiModelProperty(value = "规格值属性数组")
    private List<SkuValue> values;
}
