/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.logistics;

import com.shop.cereshop.business.page.logistics.Logistics;
import com.shop.cereshop.business.param.logistics.LogistDeleteParam;
import com.shop.cereshop.business.param.logistics.LogistSaveParam;
import com.shop.cereshop.business.param.logistics.LogistUpdateParam;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereOrderLogisticsService {
    void save(LogistSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(LogistUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(LogistDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    Logistics getById(Long logisticsId) throws CoBusinessException;

    Page getAll(ShopParam param) throws CoBusinessException;
}
