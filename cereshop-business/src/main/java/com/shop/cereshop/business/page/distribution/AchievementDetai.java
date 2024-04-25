/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.distribution;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分销员业绩详情返回数据实体
 */
@Data
@ApiModel(value = "AchievementDetai", description = "分销员业绩详情返回数据实体")
public class AchievementDetai {

    /**
     * 直接分销订单
     */
    @ApiModelProperty(value = "直接分销订单")
    private Integer directOrders;

    /**
     * 间接分销订单
     */
    @ApiModelProperty(value = "间接分销订单")
    private Integer indirectOrders;

    /**
     * 直接分销金额
     */
    @ApiModelProperty(value = "直接分销金额")
    private BigDecimal directMoney;

    /**
     * 间接分销金额
     */
    @ApiModelProperty(value = "间接分销金额")
    private BigDecimal indirectMoney;

    /**
     * 直接佣金
     */
    @ApiModelProperty(value = "直接佣金")
    private BigDecimal directCommissionMoney;

    /**
     * 间接佣金
     */
    @ApiModelProperty(value = "间接佣金")
    private BigDecimal indirectCommissionMoney;
}
