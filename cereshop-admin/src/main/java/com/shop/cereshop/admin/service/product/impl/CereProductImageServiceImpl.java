/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product.impl;

import com.shop.cereshop.admin.dao.product.CereProductImageDAO;
import com.shop.cereshop.admin.service.product.CereProductImageService;
import com.shop.cereshop.commons.domain.product.CereProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereProductImageServiceImpl implements CereProductImageService {

    @Autowired
    private CereProductImageDAO cereProductImageDAO;

    @Override
    public List<String> findByProductId(Long productId) {
        return cereProductImageDAO.findByProductId(productId);
    }
}
