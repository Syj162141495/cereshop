/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_price_product 商家定价捆绑商品信息实体
 * @author 
 */
@Data
public class CerePriceProduct implements Serializable {
    /**
     * 定价捆绑id
     */
    private Long priceId;

    /**
     * 商品id
     */
    private Long productId;

    private static final long serialVersionUID = 1L;

}
