/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.buyer;

import com.shop.cereshop.business.page.buyer.BuyerUserDetail;
import com.shop.cereshop.business.param.buyer.BuyerUserGetAllParam;
import com.shop.cereshop.business.param.buyer.UserSaveParam;
import com.shop.cereshop.business.param.buyer.UserUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereBuyerUserService {
    Page getAll(BuyerUserGetAllParam param) throws CoBusinessException;

    void save(UserSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(UserUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    BuyerUserDetail getById(Long shopId, Long buyerUserId) throws CoBusinessException;

    void initBusinessBuyerUser();

    void updateGrowth(Long buyerUserId, int growth);
}
