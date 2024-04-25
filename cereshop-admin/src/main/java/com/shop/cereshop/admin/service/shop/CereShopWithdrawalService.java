/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalHandleParam;
import com.shop.cereshop.commons.domain.shop.CereShopWithdrawal;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopWithdrawalService {
    Page getAll(WithdrawalGetAllParam param) throws CoBusinessException;

    CereShopWithdrawal getById(Long withdrawalId) throws CoBusinessException;

    void handle(WithdrawalHandleParam param, CerePlatformUser user) throws CoBusinessException;
}
