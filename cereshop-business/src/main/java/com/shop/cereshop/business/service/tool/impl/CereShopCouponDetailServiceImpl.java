/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.shop.cereshop.business.dao.tool.CereShopCouponDetailDAO;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.service.tool.CereShopCouponDetailService;
import com.shop.cereshop.commons.domain.tool.CereShopCouponDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopCouponDetailServiceImpl implements CereShopCouponDetailService {

    @Autowired
    private CereShopCouponDetailDAO cereShopCouponDetailDAO;

    @Override
    public void insertBatch(List<CereShopCouponDetail> list) throws CoBusinessException {
        cereShopCouponDetailDAO.insertBatch(list);
    }

    @Override
    public void deleteById(Long shopCouponId) throws CoBusinessException {
        cereShopCouponDetailDAO.deleteById(shopCouponId);
    }

    @Override
    public List<ToolProduct> findProducts(Long shopCouponId) {
        return cereShopCouponDetailDAO.findProducts(shopCouponId);
    }
}
