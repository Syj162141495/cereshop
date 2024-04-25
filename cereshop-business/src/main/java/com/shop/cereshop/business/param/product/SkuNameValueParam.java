/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品规格名数据
 */
@Data
@ApiModel(value = "SkuNameValueParam", description = "商品规格名和规格值数据")
public class SkuNameValueParam {

    /**
     * 规格名级别
     */
    @ApiModelProperty(value = "规格名级别")
    private String code;

    /**
     * 规格值级别
     */
    @ApiModelProperty(value = "规格值级别")
    private String valueCode;

}
