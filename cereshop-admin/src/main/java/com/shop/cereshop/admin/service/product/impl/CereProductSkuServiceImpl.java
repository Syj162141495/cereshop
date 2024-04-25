/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product.impl;

import com.shop.cereshop.admin.dao.product.CereProductSkuDAO;
import com.shop.cereshop.admin.page.product.Sku;
import com.shop.cereshop.admin.page.product.SkuNameParam;
import com.shop.cereshop.admin.page.product.SkuNameValueParam;
import com.shop.cereshop.admin.page.product.SkuValueParam;
import com.shop.cereshop.admin.service.product.CereProductSkuService;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.product.CereSkuName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereProductSkuServiceImpl implements CereProductSkuService {

    @Autowired
    private CereProductSkuDAO cereProductSkuDAO;

    @Override
    public int findStockNumber(Long skuId) {
        return cereProductSkuDAO.findStockNumber(skuId);
    }

    @Override
    public List<Sku> findByProductId(Long productId) {
        return cereProductSkuDAO.findByProductId(productId);
    }

    @Override
    public List<SkuNameParam> findNameByProductId(Long productId) {
        return cereProductSkuDAO.findNameByProductId(productId);
    }

    @Override
    public List<SkuValueParam> findByName(String skuName, Long productId) {
        return cereProductSkuDAO.findByName(skuName,productId);
    }

    @Override
    public CereSkuName findValueByProductId(Long productId) {
        return cereProductSkuDAO.findValueByProductId(productId);
    }

    @Override
    public List<SkuNameValueParam> findBySkuId(Long skuId) {
        return cereProductSkuDAO.findBySkuId(skuId);
    }
}
