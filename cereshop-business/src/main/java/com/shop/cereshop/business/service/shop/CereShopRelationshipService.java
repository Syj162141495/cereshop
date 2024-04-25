/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.param.ship.ShipGetAllParam;
import com.shop.cereshop.business.param.ship.ShipSaveParam;
import com.shop.cereshop.business.param.ship.ShipUpdateParam;
import com.shop.cereshop.business.param.shop.CereShopRelationshipParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopRelationship;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopRelationshipService {
    void save(ShipSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ShipUpdateParam relationship, CerePlatformBusiness user) throws CoBusinessException,Exception;

    Page getAll(ShipGetAllParam param) throws CoBusinessException;

    CereShopRelationship getById(Long shopId) throws CoBusinessException;
}
