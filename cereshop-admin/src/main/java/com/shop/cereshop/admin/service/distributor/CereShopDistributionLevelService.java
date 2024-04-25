/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.distributor;

import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.math.BigDecimal;
import java.util.List;

public interface CereShopDistributionLevelService {
    List<CereShopDistributionLevel> findAllByShopId(Long shopId);

    List<Long> findAllShops();

    List<CereShopDistributor> findAllDistributorByShopId(Long shopId);

    BigDecimal findMoneyByDistributor(Long distributorId);

    int findInvitationByDistributor(Long distributorId);

    int findCustomerByDistributor(Long distributorId);

    void updateBatchDistributorLevel(List<Long> ids, Long distributorLevelId) throws CoBusinessException;
}
