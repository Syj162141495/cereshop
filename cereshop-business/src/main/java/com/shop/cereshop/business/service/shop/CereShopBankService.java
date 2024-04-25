/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.page.shop.ShopBankDetail;
import com.shop.cereshop.business.param.bank.BankDeleteParam;
import com.shop.cereshop.business.param.bank.BankSaveParam;
import com.shop.cereshop.business.param.bank.BankUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.domain.shop.CereShopBank;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.apache.ibatis.annotations.Param;

public interface CereShopBankService {
    void save(BankSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(BankUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(BankDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopBankDetail getById(@Param("shopId") Long shopId) throws CoBusinessException;

    CerePlatformShop findByPhone(Long shopId, String phone);
}
