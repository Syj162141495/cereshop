/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.collect;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_collect 客户收藏信息实体
 * @author 
 */
@Data
public class CereBuyerCollect implements Serializable {
    /**
     * 收藏id
     */
    private Long collectId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 收藏类型 1-商品 2-店铺
     */
    private Integer collectType;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 是否收藏 1-是 0-否
     */
    private Integer state;

    /**
     * 是否选中 1-是 0-否
     */
    private Integer selected;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
