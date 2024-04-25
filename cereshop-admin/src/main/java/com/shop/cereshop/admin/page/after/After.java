/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.after;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 售后返回数据实体类
 */
@Data
@ApiModel(value = "After", description = "售后返回数据实体类")
public class After {

    /**
     * 售后单id
     */
    @ApiModelProperty(value = "售后单id")
    private Long afterId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    /**
     * 售后商品数量
     */
    @ApiModelProperty(value = "售后商品数量")
    private Integer number;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;
}
