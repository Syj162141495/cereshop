/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分布情况
 */
@Data
@ApiModel(value = "CityPerson", description = "分布情况")
public class Proportion {

    /**
     * 所在区域
     */
    @ApiModelProperty(value = "区域")
    private String name;

    /**
     * 占比
     */
    @ApiModelProperty(value = "占比")
    private BigDecimal value;
}
