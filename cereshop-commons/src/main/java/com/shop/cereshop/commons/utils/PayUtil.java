/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.shop.cereshop.commons.config.AlipayConfig;
import com.shop.cereshop.commons.constant.IntegerEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付相关的方法
 */
public class PayUtil {

    /**
     * 根据支付方式返回字符串
     * @param paymentMode
     * @return
     */
    public static String getPayTypeStr(Integer paymentMode) {
        if (IntegerEnum.ORDER_PAY_WX.getCode().equals(paymentMode)) {
            return "微信";
        } else if (IntegerEnum.ORDER_PAY_ALI.getCode().equals(paymentMode)) {
            return "支付宝";
        }
        return "其它支付方式";
    }

    /**
     * 支付宝获取回调参数
     * @param request
     * @return
     */
    public static Map<String, String> getAlipayResultParams(HttpServletRequest request) {
        Map requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
            String name = (String)iter.next();
            String[] values = (String [])requestParams.get(name);
            String valueStr = "";
            for(int i = 0;i < values.length;i ++ ){
                valueStr =  (i==values.length-1)?valueStr + values [i]:valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put (name,valueStr);
        }
        return params;
    }

    /**
     * 验证支付宝签名
     * @param params
     * @return
     * @throws AlipayApiException
     */
    public static boolean signAlipayVerified(Map<String, String> params) throws AlipayApiException {
        // 调用SDK验证签名
        return AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
    }

    /**
     * 解密支付宝的数据
     * @return
     */
    public static JSONObject decryptedData(String response) throws Exception {
        //1. 获取验签和解密所需要的参数
        Map<String, String> openapiResult = JSON.parseObject(response,
                new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
        String encryptType = "AES";
        String sign = openapiResult.get("sign");
        String content = openapiResult.get("response");

        //如果密文的
        boolean isDataEncrypted = !content.startsWith("{");
        boolean signCheckPass = false;

        //2. 验签
        String signContent = content;
        //如果是加密的报文则需要在密文的前后添加双引号
        if (isDataEncrypted) {
            signContent = "\"" + signContent + "\"";
        }
        try {
            signCheckPass = AlipaySignature.rsaCheck(signContent, sign, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
        } catch (AlipayApiException e) {
            //验签异常, 日志
        }
        if(!signCheckPass) {
            //验签不通过（异常或者报文被篡改），终止流程（不需要做解密）
            throw new Exception("验签失败");
        }

        //3. 解密
        String plainData = null;
        if (isDataEncrypted) {
            try {
                plainData = AlipayEncrypt.decryptContent(content, encryptType, AlipayConfig.DECRYPT_KEY, AlipayConfig.CHARSET);
            } catch (AlipayApiException e) {
                e.printStackTrace();
                //解密异常, 记录日志
                throw new Exception("解密异常");
            }
        } else {
            plainData = content;
        }
        return JSON.parseObject(plainData);
    }

}
