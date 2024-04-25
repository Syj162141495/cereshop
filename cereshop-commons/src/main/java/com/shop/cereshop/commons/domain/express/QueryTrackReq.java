/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.express;

import lombok.Data;

/**
 * @Author: api.kuaidi100.com
 * @Date: 2020-07-14 15:56
 */
@Data
public class QueryTrackReq {
    /**
     * 我方分配给贵司的的公司编号, 点击查看账号信息
     */
    private String customer;
    /**
     * 签名， 用于验证身份， 按param + key + customer 的顺序进行MD5加密（注意加密后字符串要转大写）， 不需要“+”号
     */
    private String sign;
    /**
     * 其他参数组合成的json对象
     */
    private QueryTrackParam param;
}
