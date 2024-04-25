/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.pay.alipay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shop.cereshop.admin.pay.alipay.service.AliPayService;
import com.shop.cereshop.commons.config.AlipayConfig;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.WxPayEnum;
import com.shop.cereshop.commons.exception.CoBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j(topic = "AliPayServiceImpl")
public class AliPayServiceImpl implements AliPayService {

    @Override
    public Map<String, String> refund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws CoBusinessException, Exception {
        Map<String, String> result = new HashMap<>();

        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //request.setNotifyUrl(AlipayConfig.REFUND_NOTIFY_URL);
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        //model.setOutTradeNo(outTradeNo);
        model.setTradeNo(transactionId);
        model.setRefundAmount(refund.toString());
        request.setBizModel(model);

        AlipayTradeRefundResponse response = client.execute(request);
        if (response.isSuccess()) {
            //这里只是申请退款，兼容微信的返回
            result.put("return_msg", WxPayEnum.REFUND_SUCCESS.getCode());
            result.put("return_code", WxPayEnum.REFUND_OK.getCode());
        } else {
            log.error("alipay refund failed: subCode = {}, subMsg = {}", response.getSubCode(), response.getSubMsg());
            throw new CoBusinessException(CoReturnFormat.APPLY_REFUND_FAILED);
        }
        return result;
    }

    @Override
    public Map<String, String> refundBond(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws CoBusinessException, Exception {
        Map<String, String> result = new HashMap<>();

        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //request.setNotifyUrl(AlipayConfig.REFUND_NOTIFY_URL);
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        //model.setOutTradeNo(outTradeNo);
        model.setTradeNo(transactionId);
        model.setRefundAmount(refund.toString());
        request.setBizModel(model);

        AlipayTradeRefundResponse response = client.execute(request);
        if (response.isSuccess()) {
            //这里只是申请退款，兼容微信的返回
            result.put("return_msg", WxPayEnum.REFUND_SUCCESS.getCode());
            result.put("return_code", WxPayEnum.REFUND_OK.getCode());
            result.put("out_trade_no", response.getOutTradeNo());
            result.put("transaction_id", response.getTradeNo());
        } else {
            log.error("alipay refundBond failed: subCode = {}, subMsg = {}", response.getSubCode(), response.getSubMsg());
            throw new CoBusinessException(CoReturnFormat.APPLY_REFUND_FAILED);
        }
        return result;
    }

}
