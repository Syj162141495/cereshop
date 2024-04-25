/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product;

import com.shop.cereshop.business.param.product.DeleteSkuParam;
import com.shop.cereshop.business.param.product.SkuNameParam;
import com.shop.cereshop.business.param.product.SkuNameValueParam;
import com.shop.cereshop.business.param.product.SkuValueParam;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.product.CereSkuName;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereSkuNameService {
    void insertBatch(List<CereSkuName> collect) throws CoBusinessException;

    void deleteBySkuId(Long skuId) throws CoBusinessException;

    void deleteByIds(List<Long> ids) throws CoBusinessException;

    void deleteByProductId(Long productId) throws CoBusinessException;

    List<SkuNameValueParam> findBySkuId(Long skuId);

    List<SkuNameParam> findNameBySkuId(Long skuId);

    List<SkuValueParam> findByNameAndSkuId(Long skuId, String skuName);

    CereSkuName findValueBySkuId(Long skuId);

    List<SkuNameParam> findNameByProductId(Long productId);

    List<SkuValueParam> findByName(String skuName,Long productId);

    CereSkuName findValueByProductId(Long productId);

    void insert(CereSkuName cereSkuName) throws CoBusinessException;

    List<Long> findDeleteSkuIds(List<DeleteSkuParam> deletes,Long productId);
}
