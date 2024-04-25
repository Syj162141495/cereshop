/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.page.distribution.AchievementDetai;
import com.shop.cereshop.business.param.distributor.DistributorGetAllAchievementParam;
import com.shop.cereshop.business.param.distributorOrder.OrderGetAllParam;
import com.shop.cereshop.business.param.distributorOrder.OrderSettlementParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereDistributionOrderService {
    Page getAll(OrderGetAllParam param) throws CoBusinessException;

    void settlement(OrderSettlementParam param, CerePlatformBusiness user) throws CoBusinessException;

    Page getAllAchievement(DistributorGetAllAchievementParam param) throws CoBusinessException;

    AchievementDetai getOrderDetail(Long distributorId) throws CoBusinessException;

    AchievementDetai getDealMoneyDetail(Long distributorId) throws CoBusinessException;

    AchievementDetai getCommissionMoneyDetail(Long distributorId) throws CoBusinessException;

    AchievementDetai getNotCommissionMoneyDetail(Long distributorId) throws CoBusinessException;
}
