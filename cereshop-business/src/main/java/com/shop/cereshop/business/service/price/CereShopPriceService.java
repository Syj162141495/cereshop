/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.price;

import com.shop.cereshop.business.page.price.ShopPriceDetail;
import com.shop.cereshop.business.param.price.*;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopPrice;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopPriceService {
    void save(PriceSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(PriceUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(PriceGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    void start(PriceStartParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopPriceDetail getById(Long priceId) throws CoBusinessException;

    Page getAll(PriceGetAllParam param) throws CoBusinessException;

    CereShopPrice findById(Long priceId);

    void updateState(CereShopPrice cereShopPrice) throws CoBusinessException;

    List<ShopPriceDetail> getPrices(RenovationParam param) throws CoBusinessException;
}
