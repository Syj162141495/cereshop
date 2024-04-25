/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity;

import com.shop.cereshop.commons.domain.activity.CereSignProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereSignProductService {
    void insertBatch(List<CereSignProduct> cereSignProducts) throws CoBusinessException;

    void deleteBySignId(Long signId) throws CoBusinessException;
}
