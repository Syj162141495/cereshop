/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.order.impl;

import com.shop.cereshop.admin.dao.order.CereOrderProductDAO;
import com.shop.cereshop.admin.service.order.CereOrderProductService;
import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.order.CereOrderProductAttribute;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereOrderProductServiceImpl implements CereOrderProductService {

    @Autowired
    private CereOrderProductDAO cereOrderProductDAO;

    @Override
    public void insert(CereOrderProduct orderProduct) throws CoBusinessException {
        cereOrderProductDAO.insert(orderProduct);
    }

    @Override
    public void insertBatchAttibutes(List<CereOrderProductAttribute> attributes) throws CoBusinessException {
        cereOrderProductDAO.insertBatchAttibutes(attributes);
    }
}
