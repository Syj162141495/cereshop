/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.buyer;

import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereBuyerShopCouponService {
    void insertBatch(List<CereBuyerShopCoupon> buyerShopCoupons) throws CoBusinessException;

    void updateState(CereBuyerShopCoupon cereBuyerShopCoupon) throws CoBusinessException;
}
