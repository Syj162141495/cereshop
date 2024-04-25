/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import lombok.Data;

/**
 * cere_buyer_seckill_visit 客户浏览秒杀记录实体
 * @author
 */
@Data
public class CereBuyerSeckillVisit {

    /**
     * 秒杀活动id
     */
    private Long shopSeckillId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 浏览时间
     */
    private String visitTime;
}
