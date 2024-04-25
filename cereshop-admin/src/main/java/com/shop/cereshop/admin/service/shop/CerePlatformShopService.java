/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop;

import com.shop.cereshop.admin.page.shop.ShopGetAll;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.finance.FinanceCount;
import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.finance.FinanceParam;
import com.shop.cereshop.admin.param.shop.ShopGetAllParam;
import com.shop.cereshop.admin.param.shop.ShopSaveParam;
import com.shop.cereshop.admin.param.shop.ShopStartParam;
import com.shop.cereshop.admin.param.shop.ShopUpdateParam;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformShopService {
    void save(ShopSaveParam param, CerePlatformUser user) throws CoBusinessException;

    CerePlatformShop findByShopName(String shopName);

    CerePlatformShop findByPhone(String shopPhone);

    CerePlatformShop checkShopIdByName(String shopName, Long shopId);

    CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId);

    void update(ShopUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void startOrStop(ShopStartParam param, CerePlatformUser user) throws CoBusinessException;

    ShopGetAll getById(Long shopId) throws CoBusinessException,Exception;

    Page getAll(ShopGetAllParam param) throws CoBusinessException;

    Shop findShop(Long shopId);

    void updateDate(CerePlatformShop cerePlatformShop) throws CoBusinessException;

    FinanceCount getAllFinance(FinanceParam param) throws CoBusinessException;

    Long findBusinessId(Long shopId);

    List<CerePlatformShop> findAll();

    void updateShopStop(CerePlatformShop shop) throws CoBusinessException;
}
