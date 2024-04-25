/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.order.impl;

import com.shop.cereshop.business.dao.order.CereCollageOrderDAO;
import com.shop.cereshop.business.service.order.CereCollageOrderService;
import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereCollageOrderServiceImpl implements CereCollageOrderService {

    @Autowired
    private CereCollageOrderDAO cereCollageOrderDAO;

    @Override
    public void update(CereCollageOrder cereCollageOrder) throws CoBusinessException {
        cereCollageOrderDAO.updateByPrimaryKeySelective(cereCollageOrder);
    }

    @Override
    public List<Long> findOrderIds(Long collageId) {
        return cereCollageOrderDAO.findOrderIds(collageId);
    }
}
