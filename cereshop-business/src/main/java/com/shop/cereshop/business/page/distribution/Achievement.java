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
 * 分销员业绩返回数据实体类
 */
@Data
@ApiModel(value = "Achievement", description = "分销员业绩返回数据实体类")
public class Achievement {

    /**
     * 分销员id
     */
    @ApiModelProperty(value = "分销员id")
    private Long distributorId;

    /**
     * 分销员名称
     */
    @ApiModelProperty(value = "分销员名称")
    private String distributorName;

    /**
     * 分销员手机号
     */
    @ApiModelProperty(value = "分销员手机号")
    private String distributorPhone;

    /**
     * 订单数
     */
    @ApiModelProperty(value = "订单数")
    private Integer orders;

    /**
     * 成交金额
     */
    @ApiModelProperty(value = "成交金额")
    private BigDecimal dealMoney;

    /**
     * 总的佣金
     */
    @ApiModelProperty(value = "总的佣金")
    private BigDecimal commissionMoney;

    /**
     * 未结算佣金
     */
    @ApiModelProperty(value = "未结算佣金")
    private BigDecimal unsettledMoney;
}
