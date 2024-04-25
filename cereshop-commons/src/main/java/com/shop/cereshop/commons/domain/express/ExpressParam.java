/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.express;

import lombok.Data;

/**
 * 快递API接收参数实体
 */
@Data
public class ExpressParam {

    /**
     * 单号
     */
    private String num;

    /**
     * 授权码
     */
    private String key;

    /**
     * 快递公司编号
     */
    private String com;

    /**
     * 数字签名
     */
    private String sign;
}
