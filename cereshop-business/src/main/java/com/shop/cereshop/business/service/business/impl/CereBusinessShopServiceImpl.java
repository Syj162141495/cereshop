/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.business.impl;

import com.shop.cereshop.business.dao.business.CereBusinessShopDAO;
import com.shop.cereshop.business.service.business.CereBusinessShopService;
import com.shop.cereshop.commons.domain.business.CereBusinessShop;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereBusinessShopServiceImpl implements CereBusinessShopService {

    @Autowired
    private CereBusinessShopDAO cereBusinessShopDAO;

    @Override
    public void insert(CereBusinessShop cereBusinessShop) throws CoBusinessException {
        cereBusinessShopDAO.insert(cereBusinessShop);
    }

    @Override
    public void deleteByUserId(Long businessUserId) throws CoBusinessException {
        cereBusinessShopDAO.deleteByUserId(businessUserId);
    }

    @Override
    public Long findByShopId(Long shopId) {
        return cereBusinessShopDAO.findByShopId(shopId);
    }
}
