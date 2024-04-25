/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.logistics.impl;

import com.shop.cereshop.business.dao.logistics.CereLogisticsChargeDAO;
import com.shop.cereshop.business.page.logistics.Charge;
import com.shop.cereshop.business.service.logistics.CereLogisticsChargeService;
import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereLogisticsChargeServiceImpl implements CereLogisticsChargeService {

    @Autowired
    private CereLogisticsChargeDAO cereLogisticsChargeDAO;

    @Override
    public void insertBatch(List<CereLogisticsCharge> charges) throws CoBusinessException {
        cereLogisticsChargeDAO.insertBatch(charges);
    }

    @Override
    public void deleteByLogisticsId(Long logisticsId) throws CoBusinessException {
        cereLogisticsChargeDAO.deleteByLogisticsId(logisticsId);
    }

    @Override
    public List<Charge> findByLogisticsId(Long logisticsId) {
        return cereLogisticsChargeDAO.findByLogisticsId(logisticsId);
    }
}
