/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.distributor.impl;

import com.shop.cereshop.admin.dao.distributor.CereShopDistributionLevelDAO;
import com.shop.cereshop.admin.service.distributor.CereShopDistributionLevelService;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CereShopDistributionLevelServiceImpl implements CereShopDistributionLevelService {

    @Autowired
    private CereShopDistributionLevelDAO cereShopDistributionLevelDAO;

    @Override
    public List<CereShopDistributionLevel> findAllByShopId(Long shopId) {
        return cereShopDistributionLevelDAO.findAllByShopId(shopId);
    }

    @Override
    public List<Long> findAllShops() {
        return cereShopDistributionLevelDAO.findAllShops();
    }

    @Override
    public List<CereShopDistributor> findAllDistributorByShopId(Long shopId) {
        return cereShopDistributionLevelDAO.findAllDistributorByShopId(shopId);
    }

    @Override
    public BigDecimal findMoneyByDistributor(Long distributorId) {
        return cereShopDistributionLevelDAO.findMoneyByDistributor(distributorId);
    }

    @Override
    public int findInvitationByDistributor(Long distributorId) {
        return cereShopDistributionLevelDAO.findInvitationByDistributor(distributorId);
    }

    @Override
    public int findCustomerByDistributor(Long distributorId) {
        return cereShopDistributionLevelDAO.findCustomerByDistributor(distributorId);
    }

    @Override
    public void updateBatchDistributorLevel(List<Long> ids, Long distributorLevelId) throws CoBusinessException {
        cereShopDistributionLevelDAO.updateBatchDistributorLevel(ids,distributorLevelId);
    }
}
