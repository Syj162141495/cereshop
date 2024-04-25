/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.shop.cereshop.business.dao.tool.CereShopSeckillDetailDAO;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.service.tool.CereShopSeckillDetailService;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopSeckillDetailServiceImpl implements CereShopSeckillDetailService {

    @Autowired
    private CereShopSeckillDetailDAO cereShopSeckillDetailDAO;

    @Override
    public void insertBatch(List<CereShopSeckillDetail> list) throws CoBusinessException {
        cereShopSeckillDetailDAO.insertBatch(list);
    }

    @Override
    public void deleteById(Long shopSeckillId) throws CoBusinessException {
        cereShopSeckillDetailDAO.deleteById(shopSeckillId);
    }

    @Override
    public List<ToolProduct> findProducts(Long shopSeckillId) {
        return cereShopSeckillDetailDAO.findProducts(shopSeckillId);
    }

    @Override
    public List<ToolProduct> findDistinctProducts(Long shopSeckillId) {
        return cereShopSeckillDetailDAO.findDistinctProducts(shopSeckillId);
    }

    @Override
    public List<CereShopSeckillDetail> findBySkuId(Long skuId, Long shopId) {
        return cereShopSeckillDetailDAO.findBySkuId(skuId,shopId);
    }

    @Override
    public void updateBatchSeckillPrice(List<CereShopSeckillDetail> list) throws CoBusinessException{
        cereShopSeckillDetailDAO.updateBatchSeckillPrice(list);
    }

    @Override
    public List<CereShopSeckillDetail> findNumberDetails(Long orderId, Long shopSeckillId) {
        return cereShopSeckillDetailDAO.findNumberDetails(orderId,shopSeckillId);
    }

    @Override
    public void updateBatch(List<CereShopSeckillDetail> list) throws CoBusinessException {
        cereShopSeckillDetailDAO.updateBatch(list);
    }

    @Override
    public List<Long> findProductIdList(Long shopSeckillId) {
        return cereShopSeckillDetailDAO.findProductIdList(shopSeckillId);
    }

    @Override
    public List<CereShopSeckillDetail> findDetailList(Long shopSeckillId) {
        return cereShopSeckillDetailDAO.findDetailList(shopSeckillId);
    }
}
