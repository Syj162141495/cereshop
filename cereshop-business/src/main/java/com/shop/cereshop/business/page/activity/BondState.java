/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 保证金支付状态
 */
@Data
@ApiModel(value = "BondState", description = "保证金支付状态")
public class BondState {

    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private String code;

    /**
     * 收款码
     */
    @ApiModelProperty(value = "收款码")
    private String url;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    private String type;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal price;

    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private String signCode;
}
