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
 * 商家成交排行榜数据
 */
@Data
@ApiModel(value = "ShopRanking", description = "商家成交排行榜数据")
@AllArgsConstructor
public class ShopRanking {

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 成交额
     */
    @ApiModelProperty(value = "成交额")
    private BigDecimal money;
}
