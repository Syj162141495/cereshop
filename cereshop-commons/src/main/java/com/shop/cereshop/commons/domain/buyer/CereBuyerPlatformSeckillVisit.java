/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_platform_seckill_visit 客户浏览平台秒杀记录实体
 * @author 
 */
@Data
public class CereBuyerPlatformSeckillVisit implements Serializable {
    /**
     * 平台秒杀活动id
     */
    private Long seckillId;

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
