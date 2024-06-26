/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.param.shop.CereShopPersonalParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopPersonal;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopPersonalService {
    void personal(CereShopPersonal personal, CerePlatformBusiness user) throws CoBusinessException;

    void updatePersonal(CereShopPersonal personal, CerePlatformBusiness user) throws CoBusinessException;

    CereShopPersonal findByShopId(Long shopId);

    void personalCheck(CereShopPersonalParam param, CerePlatformBusiness user) throws CoBusinessException;

    void updatePersonalCheck(CereShopPersonalParam param, CerePlatformBusiness user) throws CoBusinessException;
}
