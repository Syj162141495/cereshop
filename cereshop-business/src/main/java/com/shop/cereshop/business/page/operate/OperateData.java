/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 运营计划列表
 * @author
 */
@Data
@ApiModel(value = "ShopOperate", description = "运营计划列表")
public class OperateData {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String time;

    /**
     * 发券人数
     */
    @ApiModelProperty(value = "发券人数")
    private Integer person;

    /**
     * 下单人数
     */
    @ApiModelProperty(value = "下单人数")
    private Integer users;

    /**
     * 下单笔数
     */
    @ApiModelProperty(value = "下单笔数")
    private Integer orders;

    /**
     * 下单金额
     */
    @ApiModelProperty(value = "下单金额")
    private BigDecimal total;

    /**
     * 支付人数
     */
    @ApiModelProperty(value = "支付人数")
    private Integer pays;

    /**
     * 支付订单数
     */
    @ApiModelProperty(value = "支付订单数")
    private Integer payOrders;
}
