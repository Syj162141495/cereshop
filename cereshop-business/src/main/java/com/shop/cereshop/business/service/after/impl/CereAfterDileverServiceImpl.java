/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.after.impl;

import com.shop.cereshop.business.dao.after.CereAfterDileverDAO;
import com.shop.cereshop.business.service.after.CereAfterDileverService;
import com.shop.cereshop.commons.domain.after.CereAfterDilever;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereAfterDileverServiceImpl implements CereAfterDileverService {

    @Autowired
    private CereAfterDileverDAO cereAfterDileverDAO;

    @Override
    public void insert(CereAfterDilever cereAfterDilever) throws CoBusinessException {
        cereAfterDileverDAO.insert(cereAfterDilever);
    }
}
