/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.shop.cereshop.business.dao.activity.CereBuyerCouponDAO;
import com.shop.cereshop.business.service.activity.CereBuyerCouponService;
import com.shop.cereshop.commons.domain.activity.CereBuyerCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereBuyerCouponServiceImpl implements CereBuyerCouponService {

    @Autowired
    private CereBuyerCouponDAO cereBuyerCouponDAO;

    @Override
    public void updateByUserIdAndCouponId(CereBuyerCoupon cereBuyerCoupon) throws CoBusinessException {
        cereBuyerCouponDAO.updateByUserIdAndCouponId(cereBuyerCoupon);
    }
}
