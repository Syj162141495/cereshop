/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopExtension;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopExtensionService {
    void save(CereShopExtension shopExtension, CerePlatformBusiness user) throws CoBusinessException;

    List<CereShopExtension> getAll(ShopParam param) throws CoBusinessException;
}
