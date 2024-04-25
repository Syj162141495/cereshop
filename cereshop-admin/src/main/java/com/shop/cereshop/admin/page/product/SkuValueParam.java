/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品规格值数据
 */
@Data
@ApiModel(value = "SkuValueParam", description = "商品规格值数据")
public class SkuValueParam {

    /**
     * 规格值级别
     */
    @ApiModelProperty(value = "规格值级别")
    private String valueCode;

    /**
     * 规格值
     */
    @ApiModelProperty(value = "规格值")
    @NotBlank(message = "规格值不能为空")
    private String skuValue;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;

}
