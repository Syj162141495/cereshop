/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.label;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_shop_label 客户关联商家标签信息表
 * @author 
 */
@Data
public class CereBuyerShopLabel implements Serializable {
    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 关联商家标签id
     */
    private Long labelId;

    private static final long serialVersionUID = 1L;
}
