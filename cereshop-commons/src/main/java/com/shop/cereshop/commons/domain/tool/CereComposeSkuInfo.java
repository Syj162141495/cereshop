/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CereComposeSkuInfo {

    /**
     * id
     */
    private Long skuId;

    /**
     * sku价格
     */
    private BigDecimal price;

    /**
     * sku的名称
     */
    private String skuName;

}
