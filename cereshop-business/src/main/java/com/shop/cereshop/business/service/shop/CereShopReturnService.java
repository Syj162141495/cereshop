/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopReturn;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopReturnService {
    CereShopReturn findByShopId(Long shopId);

    void updateReturn(CereShopReturn cereShopReturn, CerePlatformBusiness user) throws CoBusinessException;

    void update(CereShopReturn shopReturn) throws CoBusinessException;

    void insert(CereShopReturn shopReturn) throws CoBusinessException;
}
