/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.shop.cereshop.business.dao.activity.CereSignProductDAO;
import com.shop.cereshop.business.service.activity.CereSignProductService;
import com.shop.cereshop.commons.domain.activity.CereSignProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereSignProductServiceImpl implements CereSignProductService {

    @Autowired
    private CereSignProductDAO cereSignProductDAO;

    @Override
    public void insertBatch(List<CereSignProduct> cereSignProducts) throws CoBusinessException {
        cereSignProductDAO.insertBatch(cereSignProducts);
    }

    @Override
    public void deleteBySignId(Long signId) throws CoBusinessException {
        cereSignProductDAO.deleteBySignId(signId);
    }
}
