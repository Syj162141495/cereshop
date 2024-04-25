/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.param.shop.CereShopOtherOrganizationsParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopOtherOrganizations;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopOtherOrganizationsService {
    void organizations(CereShopOtherOrganizations otherOrganizations, CerePlatformBusiness user) throws CoBusinessException;

    void updateOrganizations(CereShopOtherOrganizations otherOrganizations, CerePlatformBusiness user) throws CoBusinessException;

    CereShopOtherOrganizations findByShopId(Long shopId);

    void organizationsCheck(CereShopOtherOrganizationsParam param, CerePlatformBusiness user) throws CoBusinessException;

    void updateOrganizationsCheck(CereShopOtherOrganizationsParam param, CerePlatformBusiness user) throws CoBusinessException;
}
