/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop.impl;

import com.shop.cereshop.admin.dao.shop.CereShopIndividualBusinessesDAO;
import com.shop.cereshop.admin.service.shop.CereShopIndividualBusinessesService;
import com.shop.cereshop.commons.domain.shop.CereShopIndividualBusinesses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereShopIndividualBusinessesServiceImpl implements CereShopIndividualBusinessesService {

    @Autowired
    private CereShopIndividualBusinessesDAO cereShopIndividualBusinessesDAO;

    @Override
    public CereShopIndividualBusinesses findByShopId(Long shopId) {
        return cereShopIndividualBusinessesDAO.findByShopId(shopId);
    }
}
