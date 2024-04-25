/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_platform_discount_visit 客户浏览平台限时折扣记录实体
 * @author 
 */
@Data
public class CereBuyerPlatformDiscountVisit implements Serializable {
    /**
     * 平台限时折扣活动id
     */
    private Long discountId;

    /**
     * 浏览客户id
     */
    private Long buyerUserId;

    /**
     * 浏览时间
     */
    private String visitTime;

    /**
     * 店铺id
     */
    private Long shopId;

    private static final long serialVersionUID = 1L;

}
