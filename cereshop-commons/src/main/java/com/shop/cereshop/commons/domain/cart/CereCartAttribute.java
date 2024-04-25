/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_cart_attribute 购物车商品属性实体
 * @author 
 */
@Data
@ApiModel(value = "CereCartAttribute", description = "购物车商品属性实体")
public class CereCartAttribute implements Serializable {
    /**
     * 关联购物车id
     */
    @ApiModelProperty(value = "关联购物车id")
    private Long cartId;

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
