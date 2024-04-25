/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_sign_product  报名商品明细实体类
 * @author 
 */
@Data
public class CereSignProduct implements Serializable {
    /**
     * 关联报名id
     */
    private Long signId;

    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 商品限量（剩余）
     */
    private Integer number;

    /**
     * 限量总数
     */
    private Integer total;

    private static final long serialVersionUID = 1L;

}
