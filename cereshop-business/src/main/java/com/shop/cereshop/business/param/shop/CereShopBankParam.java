/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import lombok.Data;

/**
 * 收款信息接收参数实体类
 */
@Data
public class CereShopBankParam {

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 持卡人姓名
     */
    private String cardName;

    /**
     * 银行卡号
     */
    private String cardNumber;

    /**
     * 所属银行（取数据字典）
     */
    private Long bank;

    /**
     * 验证码
     */
    private String code;

    /**
     * 手机号
     */
    private String phone;
}
