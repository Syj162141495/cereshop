/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.shop.cereshop.business.dao.tool.CereShopGroupWorkDetailDAO;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.service.tool.CereShopGroupWorkDetailService;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWorkDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopGroupWorkDetailServiceImpl implements CereShopGroupWorkDetailService {

    @Autowired
    private CereShopGroupWorkDetailDAO cereShopGroupWorkDetailDAO;

    @Override
    public void insertBatch(List<CereShopGroupWorkDetail> list) throws CoBusinessException {
        cereShopGroupWorkDetailDAO.insertBatch(list);
    }

    @Override
    public void deleteById(Long shopGroupWorkId) throws CoBusinessException {
        cereShopGroupWorkDetailDAO.deleteById(shopGroupWorkId);
    }

    @Override
    public List<ToolProduct> findProducts(Long shopGroupWorkId) {
        return cereShopGroupWorkDetailDAO.findProducts(shopGroupWorkId);
    }

    @Override
    public List<ToolProduct> findDistinctProducts(Long shopGroupWorkId) {
        return cereShopGroupWorkDetailDAO.findDistinctProducts(shopGroupWorkId);
    }

    @Override
    public List<Long> findProductIdList(Long shopGroupWorkId) {
        return cereShopGroupWorkDetailDAO.findProductIdList(shopGroupWorkId);
    }
}
