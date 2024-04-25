/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.after.impl;

import com.shop.cereshop.app.dao.after.CereAfterProductDAO;
import com.shop.cereshop.app.page.cart.CartSku;
import com.shop.cereshop.app.param.after.AfterParam;
import com.shop.cereshop.app.param.settlement.ProductSku;
import com.shop.cereshop.app.service.after.CereAfterProductService;
import com.shop.cereshop.commons.domain.after.CereAfterProduct;
import com.shop.cereshop.commons.domain.after.CereAfterProductAttribute;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereAfterProductServiceImpl implements CereAfterProductService {

    @Autowired
    private CereAfterProductDAO cereAfterProductDAO;

    @Override
    public void insert(CereAfterProduct afterProduct) throws CoBusinessException {
        cereAfterProductDAO.insert(afterProduct);
    }

    @Override
    public List<CereAfterProductAttribute> findValuesBySkuId(Long skuId) {
        return cereAfterProductDAO.findValuesBySkuId(skuId);
    }

    @Override
    public List<CartSku> findSkusByAfterId(Long afterId) {
        return cereAfterProductDAO.findSkusByAfterId(afterId);
    }

    @Override
    public List<CereAfterProduct> findSkuBySkus(AfterParam param) {
        return cereAfterProductDAO.findSkuBySkus(param);
    }
}
