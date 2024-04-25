/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户订单返回数据实体类
 */
@Data
@ApiModel(value = "BuyerOrder", description = "客户订单返回数据实体类")
public class BuyerOrder {

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
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer products;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal price;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭
     */
    @ApiModelProperty(value = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭")
    private Integer state;
}
