/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AlipayConfig {

	// 应用appid
	public static String APPID;

	// 应用私钥
	public static String RSA_PRIVATE_KEY;

	// 接口内容解密key
	public static String DECRYPT_KEY;

	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY;

	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String REDIRECT_URL;

	// 退款回调地址
	public static String REFUND_NOTIFY_URL;

	// 支付成功后端回调地址
	public static String APP_NOTIFY_URL;

	// 平台活动保证金支付成功回调地址
	public static String BOND_NOTIFY_URL;

	// 花呗手续费支付方式 1-商户支付 2-用户支付
	public static Integer HUABEI_CHARGE_TYPE;

	// 花呗手续费比例列表 3期 6期 12期
	public static List<Double> HUABEI_FEERATE_LIST;

	@Value("${alipay.appid}")
	public void setAppId(String appId) {
		this.APPID = appId;
	}

	@Value("${alipay.rsa_private_key}")
	public void setRsaPrivateKey(String rsaPrivateKey) {
		this.RSA_PRIVATE_KEY = rsaPrivateKey;
	}

	@Value("${alipay.decrypt_key}")
	public void setDecryptKey(String decryptKey) {
		this.DECRYPT_KEY = decryptKey;
	}

	@Value("${alipay.alipay_public_key}")
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.ALIPAY_PUBLIC_KEY = alipayPublicKey;
	}

	@Value("${alipay.redirect_url}")
	public void setRedirectUrl(String redirectUrl) {
		this.REDIRECT_URL = redirectUrl;
	}

	@Value("${alipay.refund_notifyurl}")
	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.REFUND_NOTIFY_URL = refundNotifyUrl;
	}

	@Value("${alipay.app_notifyurl}")
	public void setAppNotifyUrl(String appNotifyUrl) {
		this.APP_NOTIFY_URL = appNotifyUrl;
	}

	@Value("${alipay.bond_notifyurl}")
	public void setBondNotifyUrl(String bondNotifyUrl) {
		this.BOND_NOTIFY_URL = bondNotifyUrl;
	}

	@Value("${alipay.huabei_charge_type}")
	public void setHuabeiChargeType(Integer huabeiChargeType) {
		this.HUABEI_CHARGE_TYPE = huabeiChargeType;
	}

	@Value("${alipay.huabei_feerate_list}")
	public void setHuabeiFeerateList(String huabeiFeerateList) {
		if (StringUtils.isNotBlank(huabeiFeerateList)) {
			String[] feeRateArr = huabeiFeerateList.split(",");
			this.HUABEI_FEERATE_LIST = Arrays.asList(Double.valueOf(feeRateArr[0]), Double.valueOf(feeRateArr[1]), Double.valueOf(feeRateArr[2]));
		}
	}

	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";

	// 编码
	public static String CHARSET = "UTF-8";

	// 返回格式
	public static String FORMAT = "json";

	// 日志记录目录定义在 logFile 中
	public static String log_path = "/log";

	// RSA2
	public static String SIGNTYPE = "RSA2";

}
