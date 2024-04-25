/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.pay.weixin.service.impl;

import com.shop.cereshop.admin.pay.weixin.service.WxPayService;
import com.shop.cereshop.admin.pay.weixin.skd.PaymentApi;
import com.shop.cereshop.admin.pay.weixin.skd.PaymentKit;
import com.shop.cereshop.admin.pay.weixin.skd.WXPayUtil;
import com.shop.cereshop.commons.constant.ParamEnum;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j(topic = "WxPayServiceImpl")
public class WxPayServiceImpl implements WxPayService {

    /**
     * 支付小程序appid
     */
    @Value("${weixin.appid}")
    private String appid;

    /**
     * 支付APP端的appid
     */
    @Value("${weixin.app_appid}")
    private String app_appid;

    /**
     * 支付小程序秘钥
     */
    @Value("${weixin.secret}")
    private String secret;

    /**
     * 商户号
     */
    @Value("${weixin.mchid}")
    private String mch_id;

    /**
     * 证书路径
     */
    @Value("${weixin.certurl}")
    private String cert_url;

    /**
     * pc回调地址
     */
    @Value("${weixin.pc_notifyurl}")
    private String pc_notify_url;

    /**
     * pc退款回调地址
     */
    @Value("${weixin.pc_refund_notifyurl}")
    private String pc_refund_notify_url;

    /**
     * pc保证金退款回调地址
     */
    @Value("${weixin.pc_bond_refund_notifyurl}")
    private String pc_bond_refund_notify_url;

    /**
     * app回调地址
     */
    @Value("${weixin.app_notifyurl}")
    private String app_notify_url;

    /**
     * 商户秘钥
     */
    @Value("${weixin.key}")
    private String key;


    @Override
    public Map<String, String> refund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws CoBusinessException, Exception {
        //退款资金来源-可用余额退款
        String refundAccount="REFUND_SOURCE_RECHARGE_FUNDS";
        Map<String, String> params = new HashMap<>();
        if(outRefundNo.contains("XCX")){
            //小程序微信退款
            params.put("appid", appid);
        }else if(outRefundNo.contains("APP")){
            //APP微信退款
            params.put("appid", app_appid);
        }
        params.put("mch_id",mch_id);
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        //商户订单号和微信订单号二选一
//        params.put("out_trade_no", wxPayLog.getOutTradeNo());
        params.put("transaction_id", transactionId);
        params.put("out_refund_no", outRefundNo);
        params.put("total_fee", total.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_fee", refund.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_account", refundAccount);
        // 退款原因，若商户传入，会在下发给用户的退款消息中体现退款原因
        params.put("refund_desc","退款");
        //退款回调
        params.put("notify_url", pc_refund_notify_url);
        //签名算法
        String sign = WXPayUtil.generateSignature(params,key);
        params.put("sign", sign);
        String xml = PaymentKit.toXml(params);
        log.info(xml);
        String xmlStr = WXPayUtil.doRefund("https://api.mch.weixin.qq.com/secapi/pay/refund", xml,cert_url,mch_id);
        log.info(xmlStr);
        //加入微信支付日志
        Map map = PaymentKit.xmlToMap(xmlStr);
        return map;
    }

    @Override
    public Map<String, String> refundBond(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws CoBusinessException, Exception {
        //退款资金来源-可用余额退款
        String refundAccount="REFUND_SOURCE_RECHARGE_FUNDS";
        Map<String, String> params = new HashMap<>();
        if(outRefundNo.contains("XCX")){
            //小程序微信退款
            params.put("appid", appid);
        }else if(outRefundNo.contains("APP")){
            //APP微信退款
            params.put("appid", app_appid);
        }
        params.put("mch_id",mch_id);
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        //商户订单号和微信订单号二选一
//        params.put("out_trade_no", wxPayLog.getOutTradeNo());
        params.put("transaction_id", transactionId);
        params.put("out_refund_no", outRefundNo);
        params.put("total_fee", total.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_fee", refund.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_account", refundAccount);
        // 退款原因，若商户传入，会在下发给用户的退款消息中体现退款原因
        params.put("refund_desc","退款");
        //退款回调
        params.put("notify_url", pc_bond_refund_notify_url);
        //签名算法
        String sign = WXPayUtil.generateSignature(params,key);
        params.put("sign", sign);
        String xml = PaymentKit.toXml(params);
        log.info(xml);
        String xmlStr = WXPayUtil.doRefund("https://api.mch.weixin.qq.com/secapi/pay/refund", xml,cert_url,mch_id);
        log.info(xmlStr);
        //加入微信支付日志
        Map map = PaymentKit.xmlToMap(xmlStr);
        return map;
    }

}
