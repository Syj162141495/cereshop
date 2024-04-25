/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.after;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_after_product 售后单商品信息实体类
 * @author 
 */
@Data
public class CereAfterProduct implements Serializable {

    /**
     * 售后单商品明细id
     */
    private Long afterProductId;

    /**
     * 关联售后单id
     */
    private Long afterId;

    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 关联规格id
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 商品售价
     */
    private BigDecimal productPrice;

    /**
     * 图片地址
     */
    private String image;

    /**
     * SKU
     */
    private String SKU;

    /**
     * 重量
     */
    private BigDecimal weight;

    private static final long serialVersionUID = 1L;

}
