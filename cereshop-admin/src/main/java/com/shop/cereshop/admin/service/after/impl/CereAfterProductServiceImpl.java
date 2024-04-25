/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.after.impl;

import com.shop.cereshop.admin.dao.after.CereAfterProductDAO;
import com.shop.cereshop.admin.service.after.CereAfterProductService;
import com.shop.cereshop.commons.domain.after.CereAfterProduct;
import com.shop.cereshop.commons.domain.after.CereAfterProductAttribute;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereAfterProductServiceImpl implements CereAfterProductService {

    @Autowired
    private CereAfterProductDAO cereAfterProductDAO;

    @Override
    public void insert(CereAfterProduct afterProduct) throws CoBusinessException {
        cereAfterProductDAO.insert(afterProduct);
    }

    @Override
    public void insertBatchAttibutes(List<CereAfterProductAttribute> attributes) throws CoBusinessException {
        cereAfterProductDAO.insertBatchAttibutes(attributes);
    }
}
