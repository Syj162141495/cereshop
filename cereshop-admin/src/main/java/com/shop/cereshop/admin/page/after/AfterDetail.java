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
import java.util.List;

/**
 * 售后详情返回数据实体类
 */
@Data
@ApiModel(value = "AfterDetail", description = "售后详情返回数据实体类")
public class AfterDetail {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 售后单id
     */
    @ApiModelProperty(value = "售后单id")
    private Long afterId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 售后单号
     */
    @ApiModelProperty(value = "售后单号")
    private String afterFormid;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private String paymentTime;

    /**
     * 支付方式 1-微信支付
     */
    @ApiModelProperty(value = "支付方式 1-微信支付")
    private String paymentMode;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderPrice;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号")
    private String deliverFormid;

    /**
     * 物流公司
     */
    @ApiModelProperty(value = "物流公司")
    private String express;

    /**
     * 售后类型 1-仅退款  2-退货退款
     */
    @ApiModelProperty(value = "售后类型 1-仅退款  2-退货退款")
    private String afterType;

    /**
     * 买家账户id
     */
    @ApiModelProperty(value = "买家账户id")
    private Long buyerUserId;

    /**
     * 生效日期
     */
    @ApiModelProperty(value = "生效日期")
    private String effectiveDate;

    /**
     * 生效年限
     */
    @ApiModelProperty(value = "生效年限")
    private Integer effectiveYear;

    /**
     * 买家账户
     */
    @ApiModelProperty(value = "买家账户")
    private String customerName;

    /**
     * 商品明细
     */
    @ApiModelProperty(value = "商品明细")
    private List<AfterProduct> products;

    /**
     * 协商历史
     */
    @ApiModelProperty(value = "协商历史")
    private List<AfterHistory> afterHistory;

    /**
     * 支付单号
     */
    @ApiModelProperty(value = "支付单号")
    private String transactionId;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭
     */
    @ApiModelProperty(value = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已关闭")
    private Integer state;

    /**
     * 售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭
     */
    @ApiModelProperty(value = "售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭")
    private Integer afterState;

    /**
     * 物流费用
     */
    @ApiModelProperty(value = "物流费用")
    private BigDecimal logisticsPrice;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountPrice;

    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal price;

    /**
     * 合同有效期
     */
    @ApiModelProperty(value = "合同有效期")
    private String effectiveTime;

    /**
     * 负责人姓名
     */
    @ApiModelProperty(value = "负责人姓名")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "负责人电话")
    private String chargePersonPhone;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String shopAdress;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundPrice;

    /**
     * 买家留言
     */
    @ApiModelProperty(value = "买家留言")
    private String explain;

    /**
     * 卖家留言
     */
    @ApiModelProperty(value = "卖家留言")
    private String reason;
}
