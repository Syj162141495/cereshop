/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product;

import com.shop.cereshop.admin.page.product.Sku;
import com.shop.cereshop.admin.page.product.SkuNameParam;
import com.shop.cereshop.admin.page.product.SkuNameValueParam;
import com.shop.cereshop.admin.page.product.SkuValueParam;
import com.shop.cereshop.commons.domain.product.CereSkuName;

import java.util.List;

public interface CereProductSkuService {
    int findStockNumber(Long skuId);

    List<Sku> findByProductId(Long productId);

    List<SkuNameParam> findNameByProductId(Long productId);

    List<SkuValueParam> findByName(String skuName, Long productId);

    CereSkuName findValueByProductId(Long productId);

    List<SkuNameValueParam> findBySkuId(Long skuId);
}
