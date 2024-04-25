/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.activity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_buyer_coupon_detail 客户优惠券优惠明细实体
 * @author 
 */
@Data
public class CereBuyerToolDetail implements Serializable {
    /**
     * 关联优惠券id
     */
    private Long toolId;

    /**
     * 满多少元
     */
    private BigDecimal fullMoney;

    /**
     * 减多少元
     */
    private BigDecimal reduceMoney;

    private static final long serialVersionUID = 1L;
}
