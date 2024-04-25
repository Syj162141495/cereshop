/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.page.shop.ShopBanner;
import com.shop.cereshop.business.param.banner.BannerSaveParam;
import com.shop.cereshop.business.param.banner.BannerUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopBannerService {
    void save(BannerSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(BannerUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopBanner getById(Long bannerId) throws CoBusinessException;
}
