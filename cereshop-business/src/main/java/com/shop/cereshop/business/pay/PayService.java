/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.pay;

import com.shop.cereshop.commons.exception.CoBusinessException;

import java.math.BigDecimal;
import java.util.Map;

public interface PayService {

    /**
     * 退款
     * @param outTradeNo 支付时使用的商家订单号
     * @param transactionId 支付单号
     * @param outRefundNo 支付生成的退款单号
     * @param total 订单总金额
     * @param refund 退款金额
     * @param afterId 退款单的id
     * @throws CoBusinessException
     * @throws Exception
     */
    Map<String,String> refund(String outTradeNo, String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund, Long afterId) throws CoBusinessException, Exception;


    /**
     * 生成收款码返回
     * @param orderFormid 订单编号
     * @param money 金额
     * @param ip IP地址
     * @param tradeType 支付类型
     * @throws Exception
     */
    String getCollectionCode(String orderFormid, BigDecimal money, String ip, String tradeType) throws CoBusinessException, Exception;

    /**
     * 订单取消微信退款
     * @param transactionId 支付单号
     * @param outRefundNo 支付生成的退款单号
     * @param total 订单总金额
     * @param refund 退款金额
     * @throws CoBusinessException
     * @throws Exception
     */
    public Map<String,String> orderRefund(String transactionId,String outRefundNo,BigDecimal total,BigDecimal refund) throws CoBusinessException,Exception;

}
