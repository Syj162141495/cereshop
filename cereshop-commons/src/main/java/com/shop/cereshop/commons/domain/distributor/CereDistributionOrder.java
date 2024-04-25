/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.distributor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_distribution_order 分销员订单信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereDistributionOrder", description = "分销员订单信息实体类")
public class CereDistributionOrder implements Serializable {
    /**
     * 关联订单id
     */
    @ApiModelProperty(value = "关联订单id")
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 关联分销员id
     */
    @ApiModelProperty(value = "关联分销员id")
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
     * 订单实付金额
     */
    @ApiModelProperty(value = "订单实付金额")
    private BigDecimal price;

    /**
     * 佣金
     */
    @ApiModelProperty(value = "佣金")
    private BigDecimal commission;

    /**
     * 是否结算 1-是 0-否
     */
    @ApiModelProperty(value = "是否结算 1-是 0-否")
    private Integer state;

    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间")
    private String transactionTime;

    /**
     * 佣金类型 1-直接佣金 2-间接佣金
     */
    @ApiModelProperty(value = "佣金类型 1-直接佣金 2-间接佣金")
    private Integer type;

    private static final long serialVersionUID = 1L;
}
