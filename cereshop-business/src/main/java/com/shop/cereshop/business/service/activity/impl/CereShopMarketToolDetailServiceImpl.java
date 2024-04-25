/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.shop.cereshop.business.dao.activity.CereShopMarketToolDetailDAO;
import com.shop.cereshop.business.service.activity.CereShopMarketToolDetailService;
import com.shop.cereshop.commons.domain.activity.CereShopMarketToolDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopMarketToolDetailServiceImpl implements CereShopMarketToolDetailService {

    @Autowired
    private CereShopMarketToolDetailDAO cereShopMarketToolDetailDAO;

    @Override
    public void insertBatch(List<CereShopMarketToolDetail> details) throws CoBusinessException {
        cereShopMarketToolDetailDAO.insertBatch(details);
    }

    @Override
    public void deleteByToolId(Long toolId) throws CoBusinessException {
        cereShopMarketToolDetailDAO.deleteByToolId(toolId);
    }

    @Override
    public List<CereShopMarketToolDetail> findByToolId(Long toolId) {
        return cereShopMarketToolDetailDAO.findByToolId(toolId);
    }
}
