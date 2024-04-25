/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.page.index.Index;
import com.shop.cereshop.business.page.shop.Shop;
import com.shop.cereshop.business.param.index.IndexParam;
import com.shop.cereshop.business.param.shop.ShopUpdateLogoParam;
import com.shop.cereshop.commons.domain.business.CereBusinessShop;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformShopService {

    CerePlatformShop selectByPrimaryKey(Long shopId);

    CerePlatformShop findByShopName(String shopName);

    Shop getById(Long shopId) throws CoBusinessException;

    void update(ShopUpdateLogoParam param, CerePlatformBusiness user) throws CoBusinessException;

    void updateState(CerePlatformShop cerePlatformShop) throws CoBusinessException;

    Index index(IndexParam param) throws CoBusinessException;

    CerePlatformShop check(String shopPhone) throws CoBusinessException;

    void insert(CerePlatformShop cerePlatformShop) throws CoBusinessException;

    CerePlatformShop findByPhone(String shopPhone);

    void update(CerePlatformShop cerePlatformShop) throws CoBusinessException;

    CerePlatformShop checkShopIdByName(String shopName, Long shopId);

    CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId);

    Index indexTest(IndexParam param) throws CoBusinessException;

    Integer findPayUsers(Long productId);

    List<CereBusinessShop> findIds();

    Long findShopRoleId(Long id);

    List<CerePlatformShop> selectAll();
}
