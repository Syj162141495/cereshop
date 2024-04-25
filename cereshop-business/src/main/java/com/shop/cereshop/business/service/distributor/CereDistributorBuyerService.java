/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor;

import com.shop.cereshop.business.param.ship.ShipBindParam;
import com.shop.cereshop.business.param.ship.ShipDeleteParam;
import com.shop.cereshop.business.param.ship.ShipRelieveParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereDistributorBuyerService {
    void bind(ShipBindParam buyer, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void relieveBind(ShipRelieveParam buyer, CerePlatformBusiness user) throws CoBusinessException;

    void delete(ShipDeleteParam buyer, CerePlatformBusiness user) throws CoBusinessException;
}
