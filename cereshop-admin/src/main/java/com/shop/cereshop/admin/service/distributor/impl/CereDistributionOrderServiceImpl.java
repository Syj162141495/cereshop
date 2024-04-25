/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.distributor.impl;

import com.shop.cereshop.admin.dao.distributor.CereDistributionOrderDAO;
import com.shop.cereshop.admin.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.commons.domain.distributor.CereDistributionOrder;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereDistributionOrderServiceImpl implements CereDistributionOrderService {

    @Autowired
    private CereDistributionOrderDAO cereDistributionOrderDAO;

    @Override
    public void insert(CereDistributionOrder distributionOrder) throws CoBusinessException {
        cereDistributionOrderDAO.insert(distributionOrder);
    }
}
