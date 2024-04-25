/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.collect;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_footprint 商品足迹信息实体
 * @author 
 */
@Data
public class CereBuyerFootprint implements Serializable {
    /**
     * 足迹id
     */
    private Long footprintId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 是否选中 1-是 0-否
     */
    private Integer selected;

    private String createTime;

    private static final long serialVersionUID = 1L;

}
