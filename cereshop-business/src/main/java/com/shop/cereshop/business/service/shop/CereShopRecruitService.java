/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.param.recruit.ShopRecruitParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopRecruitService {
    void save(ShopRecruitParam recruit, CerePlatformBusiness user) throws CoBusinessException;

    void update(ShopRecruitParam recruit, CerePlatformBusiness user) throws CoBusinessException;

    ShopRecruitParam getByShopId(Long shopId) throws CoBusinessException;
}
