/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.buyer;

import com.shop.cereshop.admin.page.buyer.BuyerWithdrawal;
import com.shop.cereshop.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.cereshop.admin.param.withdrawal.BuyerWithdrawalGetByIdParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereBuyerWithdrawalService {
    Page getAll(BuyerWithdrawalGetAllParam param) throws CoBusinessException;

    BuyerWithdrawal getById(Long withdrawalId) throws CoBusinessException;

    void handle(BuyerWithdrawalGetByIdParam param, CerePlatformUser user) throws CoBusinessException;
}
