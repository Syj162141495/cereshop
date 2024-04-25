/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.order.impl;

import com.shop.cereshop.admin.dao.order.CereOrderParentDAO;
import com.shop.cereshop.admin.service.order.CereOrderParentService;
import com.shop.cereshop.commons.domain.order.CereOrderParent;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereOrderParentServiceImpl implements CereOrderParentService {

    @Autowired
    private CereOrderParentDAO cereOrderParentDAO;

    @Override
    public void insert(CereOrderParent parent) throws CoBusinessException {
        cereOrderParentDAO.insert(parent);
    }
}
