/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 财务返回数据实体类
 */
@Data
@ApiModel(value = "Finance", description = "财务返回数据实体类")
public class Finance {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String time;

    /**
     * 收入
     */
    @ApiModelProperty(value = "收入")
    private BigDecimal income;

    /**
     * 支出
     */
    @ApiModelProperty(value = "支出")
    private BigDecimal expenditure;
}
