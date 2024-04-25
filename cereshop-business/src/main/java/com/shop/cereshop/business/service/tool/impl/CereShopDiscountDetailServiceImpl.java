/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.shop.cereshop.business.dao.tool.CereShopDiscountDetailDAO;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.service.tool.CereShopDiscountDetailService;
import com.shop.cereshop.commons.domain.tool.CereShopDiscountDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopDiscountDetailServiceImpl implements CereShopDiscountDetailService {

    @Autowired
    private CereShopDiscountDetailDAO cereShopDiscountDetailDAO;

    @Override
    public void insertBatch(List<CereShopDiscountDetail> list) throws CoBusinessException {
        cereShopDiscountDetailDAO.insertBatch(list);
    }

    @Override
    public void deleteById(Long shopDiscountId) throws CoBusinessException {
        cereShopDiscountDetailDAO.deleteById(shopDiscountId);
    }

    @Override
    public List<ToolProduct> findProducts(Long shopDiscountId) {
        return cereShopDiscountDetailDAO.findProducts(shopDiscountId);
    }

    @Override
    public List<ToolProduct> findDistinctProducts(Long shopDiscountId) {
        return cereShopDiscountDetailDAO.findDistinctProducts(shopDiscountId);
    }

    @Override
    public List<CereShopDiscountDetail> findBySkuId(Long skuId, Long shopId) {
        return cereShopDiscountDetailDAO.findBySkuId(skuId,shopId);
    }

    @Override
    public void updateBatchDiscountPrice(List<CereShopDiscountDetail> list) throws CoBusinessException {
        cereShopDiscountDetailDAO.updateBatchDiscountPrice(list);
    }

    @Override
    public List<CereShopDiscountDetail> findNumberDetails(Long orderId, Long shopDiscountId) {
        return cereShopDiscountDetailDAO.findNumberDetails(orderId,shopDiscountId);
    }

    @Override
    public void updateBatch(List<CereShopDiscountDetail> list) throws CoBusinessException {
        cereShopDiscountDetailDAO.updateBatch(list);
    }

    @Override
    public List<Long> findProductIdList(Long shopDiscountId) {
        return cereShopDiscountDetailDAO.findProductIdList(shopDiscountId);
    }

    @Override
    public List<CereShopDiscountDetail> findDetailList(Long shopDiscountId) {
        return cereShopDiscountDetailDAO.findDetailList(shopDiscountId);
    }
}
