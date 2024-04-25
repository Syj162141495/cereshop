/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product.impl;

import com.shop.cereshop.business.dao.product.CereProductSkuDAO;
import com.shop.cereshop.business.page.product.Sku;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.product.DeleteSkuParam;
import com.shop.cereshop.business.service.product.CereProductSkuService;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereProductSkuServiceImpl implements CereProductSkuService {

    @Autowired
    private CereProductSkuDAO cereProductSkuDAO;

    @Override
    public void update(CereProductSku cereProductSku) throws CoBusinessException {
        cereProductSkuDAO.updateByPrimaryKeySelective(cereProductSku);
    }

    @Override
    public void insertBatch(List<CereProductSku> adds) throws CoBusinessException {
        cereProductSkuDAO.insertBatch(adds);
    }

    @Override
    public void deleteByIds(List<Long> ids) throws CoBusinessException {
        cereProductSkuDAO.deleteByIds(ids);
    }

    @Override
    public void deleteByProductId(Long productId) throws CoBusinessException {
        cereProductSkuDAO.deleteByProductId(productId);
    }

    @Override
    public List<Sku> findByProductId(Long productId) {
        return cereProductSkuDAO.findByProductId(productId);
    }

    @Override
    public Integer findVolumeByProductId(Long productId) {
        return cereProductSkuDAO.findVolumeByProductId(productId);
    }

    @Override
    public void insert(CereProductSku cereProductSku) throws CoBusinessException {
        cereProductSkuDAO.insert(cereProductSku);
    }

    @Override
    public int findStockNumber(Long skuId) {
        return cereProductSkuDAO.findStockNumber(skuId);
    }

    @Override
    public List<ToolProduct> getToolSkus(Long productId) {
        return cereProductSkuDAO.getToolSkus(productId);
    }

    @Override
    public List<CereProductSku> findStockNumberByOrderId(Long orderId) {
        return cereProductSkuDAO.findStockNumberByOrderId(orderId);
    }

    @Override
    public int findStockNumberBySkuId(Long skuId) {
        return cereProductSkuDAO.findStockNumberBySkuId(skuId);
    }

    @Override
    public void updateBatchSkus(List<CereProductSku> list) {
        cereProductSkuDAO.updateBatchSkus(list);
    }
}
