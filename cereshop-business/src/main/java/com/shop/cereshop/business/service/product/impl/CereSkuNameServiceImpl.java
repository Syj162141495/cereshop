/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product.impl;

import com.shop.cereshop.business.dao.product.CereSkuNameDAO;
import com.shop.cereshop.business.param.product.DeleteSkuParam;
import com.shop.cereshop.business.param.product.SkuNameParam;
import com.shop.cereshop.business.param.product.SkuNameValueParam;
import com.shop.cereshop.business.param.product.SkuValueParam;
import com.shop.cereshop.business.service.product.CereSkuNameService;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.product.CereSkuName;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereSkuNameServiceImpl implements CereSkuNameService {

    @Autowired
    private CereSkuNameDAO cereSkuNameDAO;

    @Override
    public void insertBatch(List<CereSkuName> collect) throws CoBusinessException {
        cereSkuNameDAO.insertBatch(collect);
    }

    @Override
    public void deleteBySkuId(Long skuId) throws CoBusinessException {
        cereSkuNameDAO.deleteByPrimaryKey(skuId);
    }

    @Override
    public void deleteByIds(List<Long> ids) throws CoBusinessException {
        cereSkuNameDAO.deleteByIds(ids);
    }

    @Override
    public void deleteByProductId(Long productId) throws CoBusinessException {
        cereSkuNameDAO.deleteByProductId(productId);
    }

    @Override
    public List<SkuNameValueParam> findBySkuId(Long skuId) {
        return cereSkuNameDAO.findBySkuId(skuId);
    }

    @Override
    public List<SkuNameParam> findNameBySkuId(Long skuId) {
        return cereSkuNameDAO.findNameBySkuId(skuId);
    }

    @Override
    public List<SkuValueParam> findByNameAndSkuId(Long skuId, String skuName) {
        return cereSkuNameDAO.findByNameAndSkuId(skuId,skuName);
    }

    @Override
    public CereSkuName findValueBySkuId(Long skuId) {
        return cereSkuNameDAO.findValueBySkuId(skuId);
    }

    @Override
    public List<SkuNameParam> findNameByProductId(Long productId) {
        return cereSkuNameDAO.findNameByProductId(productId);
    }

    @Override
    public List<SkuValueParam> findByName(String skuName,Long productId) {
        return cereSkuNameDAO.findByName(skuName,productId);
    }

    @Override
    public CereSkuName findValueByProductId(Long productId) {
        return cereSkuNameDAO.findValueByProductId(productId);
    }

    @Override
    public void insert(CereSkuName cereSkuName) throws CoBusinessException {
        cereSkuNameDAO.insert(cereSkuName);
    }

    @Override
    public List<Long> findDeleteSkuIds(List<DeleteSkuParam> deletes,Long productId) {
        return cereSkuNameDAO.findDeleteSkuIds(deletes,productId);
    }
}
