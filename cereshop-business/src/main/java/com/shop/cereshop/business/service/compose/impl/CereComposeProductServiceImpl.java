/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.compose.impl;

import com.shop.cereshop.business.dao.compose.CereComposeProductDAO;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.business.service.compose.CereComposeProductService;
import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereComposeProductServiceImpl implements CereComposeProductService {

    @Autowired
    private CereComposeProductDAO cereComposeProductDAO;

    @Override
    public void insertBatch(List<CereComposeProduct> composeProducts) throws CoBusinessException {
        cereComposeProductDAO.insertBatch(composeProducts);
    }

    @Override
    public void deleteByComposeId(Long composeId) throws CoBusinessException {
        cereComposeProductDAO.deleteByComposeId(composeId);
    }

    @Override
    public List<ComposeProduct> findProducts(Long composeId) {
        return cereComposeProductDAO.findProducts(composeId);
    }
}
