/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_product_member 商品会员信息实体
 * @author 
 */
@Data
public class CereProductMember implements Serializable {
    /**
     * 商品id
     */
    private Long productId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 优惠方式   1-折扣 2-指定价格
     */
    private Integer mode;

    /**
     * 多少元/几折
     */
    private BigDecimal price;

    /**
     * 最终价格
     */
    private BigDecimal total;

    private static final long serialVersionUID = 1L;

}
