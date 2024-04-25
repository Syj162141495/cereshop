/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.order.impl;

import com.shop.cereshop.business.dao.order.CereCollageOrderDetailDAO;
import com.shop.cereshop.business.service.order.CereCollageOrderDetailService;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereCollageOrderDetailServiceImpl implements CereCollageOrderDetailService {

    @Autowired
    private CereCollageOrderDetailDAO cereCollageOrderDetailDAO;

    @Override
    public void updateInvalid(List<Long> ids) throws CoBusinessException {
        cereCollageOrderDetailDAO.updateInvalid(ids);
    }
}
