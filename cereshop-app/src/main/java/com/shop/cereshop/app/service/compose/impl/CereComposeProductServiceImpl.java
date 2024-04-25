/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.compose.impl;

import com.shop.cereshop.app.dao.compose.CereComposeProductDAO;
import com.shop.cereshop.app.service.compose.CereComposeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereComposeProductServiceImpl implements CereComposeProductService {

    @Autowired
    private CereComposeProductDAO cereComposeProductDAO;

    @Override
    public List<Long> selectByComposeId(Long composeId) {
        return cereComposeProductDAO.selectByComposeId(composeId);
    }
}
