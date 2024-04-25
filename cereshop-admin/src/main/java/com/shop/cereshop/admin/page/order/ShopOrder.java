/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.order;

import com.shop.cereshop.admin.page.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单返回数据实体类
 */
@Data
@ApiModel(value = "ShopOrder", description = "订单返回数据实体类")
public class ShopOrder {
    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    private String shopName;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

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
     * 支付单号
     */
    @ApiModelProperty(value = "支付单号")
    private String transactionId;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer state;

    /**
     * 售后状态
     */
    @ApiModelProperty(value = "售后状态")
    private Integer afterState;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private Integer paymentMode;

    /**
     * 物流方案名称
     */
    @ApiModelProperty(value = "物流方案名称")
    private String logisticsName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private String paymentTime;

    /**
     * 下单账户
     */
    @ApiModelProperty(value = "下单账户")
    private String customerName;

    /**
     * 下单总数
     */
    @ApiModelProperty(value = "下单总数")
    private Integer total;

    /**
     * 下单备注
     */
    @ApiModelProperty(value = "下单备注")
    private String remark;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String receiveName;

    /**
     * 收货人手机号
     */
    @ApiModelProperty(value = "收货人手机号")
    private String receivePhone;

    /**
     * 收货人地址
     */
    @ApiModelProperty(value = "收货人地址")
    private String receiveAdress;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    private String postalCode;

    /**
     * 商品总价
     */
    @ApiModelProperty(value = "商品总价")
    private BigDecimal orderPrice;

    /**
     * 物流费用
     */
    @ApiModelProperty(value = "物流费用")
    private BigDecimal logisticsPrice;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal price;

    /**
     * 商品信息数据
     */
    @ApiModelProperty(value = "商品信息数据")
    private List<Product> products;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer number;

    /**
     * 售后单号
     */
    @ApiModelProperty(value = "售后单号")
    private String afterFormIds;
}
