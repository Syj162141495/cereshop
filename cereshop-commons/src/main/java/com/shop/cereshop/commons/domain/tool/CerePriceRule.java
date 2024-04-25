/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_price_rule 商家定价捆绑优惠规则信息实体
 * @author
 */
@Data
@ApiModel("定价捆绑规则对象")
public class CerePriceRule implements Serializable {
    /**
     * 定价捆绑id
     */
    @ApiModelProperty("定价捆绑id")
    private Long priceId;

    /**
     * 任选几件
     */
    @ApiModelProperty("任选几件")
    private Integer number;

    /**
     * 捆绑价
     */
    @ApiModelProperty("捆绑价")
    private BigDecimal price;

    private static final long serialVersionUID = 1L;
}
