/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 组合捆绑销售的商品信息
 */
@Data
public class CereComposeProductInfo implements Serializable {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品的sku详情
     */
    private List<CereComposeSkuInfo> composeSkuInfoList;
}
