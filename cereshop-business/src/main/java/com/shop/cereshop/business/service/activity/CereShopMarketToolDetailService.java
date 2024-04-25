/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity;

import com.shop.cereshop.commons.domain.activity.CereShopMarketToolDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopMarketToolDetailService {
    void insertBatch(List<CereShopMarketToolDetail> details) throws CoBusinessException;

    void deleteByToolId(Long toolId) throws CoBusinessException;

    List<CereShopMarketToolDetail> findByToolId(Long toolId);
}
