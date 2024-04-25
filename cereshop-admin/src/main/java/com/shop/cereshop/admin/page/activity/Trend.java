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
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 趋势图
 */
@Data
@ApiModel(value = "Trend", description = "趋势图")
@AllArgsConstructor
@NoArgsConstructor
public class Trend {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private List<String> times;


    /**
     * 销售额
     */
    @ApiModelProperty(value = "销售额")
    private List<BigDecimal> moneys;


    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private List<Integer> totals;
}
