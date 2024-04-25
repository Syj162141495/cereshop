/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.coupon;

import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.app.param.canvas.CanvasCouponParam;
import com.shop.cereshop.app.param.coupon.ShopCouponParam;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopCouponService {
    List<ProductCoupon> findByProductId(Long productId) ;

    void takeCoupon(ShopCouponParam param, CereBuyerUser user) throws CoBusinessException;

    CereShopCoupon findById(Long shopCouponId);

    void updateByPrimaryKeySelective(CereShopCoupon cereShopCoupon) throws CoBusinessException;

    Page getShopCoupons(CanvasCouponParam param,CereBuyerUser user) throws CoBusinessException;

    List<ProductCoupon> findByProductIdAndUserId(Long buyerUserId, Long productId);

    List<CereShopCoupon> findOnGoingCouponByBatchId(List<Long> couponIdList);

    List<CereBuyerUser> selectCanTakeCouponUser(Long shopId, Long couponId, Integer receiveType, Integer frequency);
}
