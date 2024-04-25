/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.pay;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_pay_log 支付日志实体
 * @author 
 */
@Data
public class CerePayLog implements Serializable {
    private Long id;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 关联订单编号
     */
    private String orderFormid;

    /**
     * 订单支付编号
     */
    private String outTradeNo;

    /**
     * 支付生成的订单号
     */
    private String transactionId;

    /**
     * 商户系统内部的退款单号,商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。退货流水号
     */
    private String outRefundNo;

    /**
     * 订单总金额
     */
    private BigDecimal totalFee;

    /**
     * 退款总金额，订单总金额，单位为分
     */
    private BigDecimal refundFee;

    /**
     * 支付或退款用户ID
     */
    private String userId;

    /**
     * 支付、退款、提现
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式 1-微信 2-支付宝
     */
    private Integer paymentMode;

    /**
     * 售后单id拼接字符串
     */
    private String after;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

}
