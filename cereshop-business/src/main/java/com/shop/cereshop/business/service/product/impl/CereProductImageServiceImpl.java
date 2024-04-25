/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product.impl;

import com.shop.cereshop.business.dao.product.CereProductImageDAO;
import com.shop.cereshop.business.service.product.CereProductImageService;
import com.shop.cereshop.commons.domain.product.CereProductImage;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereProductImageServiceImpl implements CereProductImageService {

    @Autowired
    private CereProductImageDAO cereProductImageDAO;

    @Override
    public void insertBatch(List<CereProductImage> list) throws CoBusinessException {
        cereProductImageDAO.insertBatch(list);
    }

    @Override
    public void deleteByProductId(Long productId) throws CoBusinessException {
        cereProductImageDAO.deleteByProductId(productId);
    }

    @Override
    public List<String> findByProductId(Long productId) {
        return cereProductImageDAO.findByProductId(productId);
    }
}
