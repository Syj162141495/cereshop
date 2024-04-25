/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.buyer.impl;

import com.shop.cereshop.business.dao.buyer.CereBuyerShopCouponDAO;
import com.shop.cereshop.business.service.buyer.CereBuyerShopCouponService;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereBuyerShopCouponServiceImpl implements CereBuyerShopCouponService {

    @Autowired
    private CereBuyerShopCouponDAO cereBuyerShopCouponDAO;

    @Override
    public void insertBatch(List<CereBuyerShopCoupon> list) throws CoBusinessException {
        cereBuyerShopCouponDAO.insertBatch(list);
    }

    @Override
    public void updateState(CereBuyerShopCoupon cereBuyerShopCoupon) throws CoBusinessException {
        cereBuyerShopCouponDAO.updateState(cereBuyerShopCoupon);
    }
}
