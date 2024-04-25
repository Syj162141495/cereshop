/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import lombok.Data;

/**
 * cere_buyer_discount_visit 客户浏览限时折扣记录实体
 * @author
 */
@Data
public class CereBuyerDiscountVisit {

    /**
     * 限时折扣活动id
     */
    private Long shopDiscountId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 浏览时间
     */
    private String visitTime;
}
