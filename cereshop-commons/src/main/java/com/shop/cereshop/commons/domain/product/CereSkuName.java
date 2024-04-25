/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import com.shop.cereshop.commons.constant.IntegerEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_product_sku 商品规格属性信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereSkuName", description = "商品规格属性信息实体类")
public class CereSkuName implements Serializable {

    /**
     * 规格id
     */
    @ApiModelProperty(value = "规格id")
    private Long skuId;

    /**
     * 是否需要配图 1-是 0-否
     */
    @ApiModelProperty(value = "是否需要配图 1-是 0-否")
    private Integer need;

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
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String image;

    /**
     * 规格名级别
     */
    @ApiModelProperty(value = "规格名级别")
    private String nameCode;

    /**
     * 规格值级别
     */
    @ApiModelProperty(value = "规格值级别")
    private String valueCode;

    private static final long serialVersionUID = 1L;

}
