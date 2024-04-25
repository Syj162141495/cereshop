/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product;

import com.shop.cereshop.commons.domain.product.CereProductImage;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereProductImageService {
    void insertBatch(List<CereProductImage> list) throws CoBusinessException;

    void deleteByProductId(Long productId) throws CoBusinessException;

    List<String> findByProductId(Long productId);
}
