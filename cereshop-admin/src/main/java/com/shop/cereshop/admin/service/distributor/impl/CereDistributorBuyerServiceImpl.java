/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.distributor.impl;

import com.shop.cereshop.admin.dao.distributor.CereDistributorBuyerDAO;
import com.shop.cereshop.admin.service.distributor.CereDistributorBuyerService;
import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereDistributorBuyerServiceImpl implements CereDistributorBuyerService {

    @Autowired
    private CereDistributorBuyerDAO cereDistributorBuyerDAO;

    @Override
    public void insert(CereDistributorBuyer distributorBuyer) throws CoBusinessException {
        cereDistributorBuyerDAO.insert(distributorBuyer);
    }
}
