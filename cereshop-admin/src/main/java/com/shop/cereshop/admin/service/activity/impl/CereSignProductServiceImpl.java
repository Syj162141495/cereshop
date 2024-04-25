/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity.impl;

import com.shop.cereshop.admin.dao.activity.CereSignProductDAO;
import com.shop.cereshop.admin.service.activity.CereSignProductService;
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
    public void deleteById(Long signId) throws CoBusinessException {
        cereSignProductDAO.deleteById(signId);
    }

    @Override
    public List<CereSignProduct> selectSignProductList(Integer signType, Long activityId) {
        return cereSignProductDAO.selectSignProductList(signType, activityId);
    }
}
