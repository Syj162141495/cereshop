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
 * 流水明细返回数据实体类
 */
@Data
@ApiModel(value = "FlowingWater", description = "流水明细返回数据实体类")
public class FlowingWater {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 流水类型
     */
    @ApiModelProperty(value = "流水类型")
    private String waterType;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 收支类型
     */
    @ApiModelProperty(value = "收支类型")
    private String incomeType;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String time;
}
