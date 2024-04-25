/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.pay;

import com.shop.cereshop.commons.exception.CoBusinessException;

import java.math.BigDecimal;
import java.util.Map;

public interface PayService {

    /**
     * 售后微信退款
     * @param transactionId 第三方支付单号
     * @param outRefundNo 支付生成的退款单号
     * @param total 订单总金额
     * @param refund 退款金额
     * @throws CoBusinessException
     * @throws Exception
     */
    Map<String,String> refund(String transactionId,String outRefundNo,BigDecimal total,BigDecimal refund) throws CoBusinessException,Exception;

    /**
     * 保证金退款
     * @param transactionId 支付单号
     * @param outRefundNo 支付生成的退款单号
     * @param total 订单总金额
     * @param refund 退款金额
     * @throws CoBusinessException
     * @throws Exception
     */
    Map<String,String> refundBond(String transactionId,String outRefundNo,BigDecimal total,BigDecimal refund) throws CoBusinessException,Exception;

}
