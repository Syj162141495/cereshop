/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.platformtool;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户支付有礼记录
 */
@Data
public class CereBuyerPoliteRecord implements Serializable {
    private static final long serialVersionUID = 5149776432020413421L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long buyerUserId;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 关联的支付有礼活动id
     */
    private Long politeId;

    /**
     * 奖励类型 1-成长值，2-满减券，3-折扣券
     */
    private Integer politeType;

    /**
     * 奖励的成长值
     */
    private Integer growth;

    /**
     * 奖励的优惠券金额，或折扣券的折扣额度
     */
    private BigDecimal discount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
