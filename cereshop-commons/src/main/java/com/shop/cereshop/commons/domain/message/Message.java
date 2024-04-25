/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.message;

import lombok.Data;

@Data
public class Message {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 返回编码
     */
    private String code;

    /**
     * 返回提示信息
     */
    private String msg;

    /**
     * 时间
     */
    private String receiveTime;
}
