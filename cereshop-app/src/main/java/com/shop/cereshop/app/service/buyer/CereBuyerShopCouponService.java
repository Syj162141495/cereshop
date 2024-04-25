/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.buyer;

import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.app.param.order.OrderProductParam;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.math.BigDecimal;
import java.util.List;

public interface CereBuyerShopCouponService {
    List<ProductCoupon> findCouponByProduct(BigDecimal total, Long buyerUserId, Long productId);

    CereBuyerShopCoupon findById(Long id);

    void updateState(CereBuyerShopCoupon cereBuyerShopCoupon) throws CoBusinessException;

    void insert(CereBuyerShopCoupon cereBuyerShopCoupon) throws CoBusinessException;

    int findCount(Long buyerUserId, Long shopCouponId);

    List<ProductCoupon> getCoupons(Long buyerUserId, Integer state);

    void updateBuyerData(Long buyerUserId, Long id) throws CoBusinessException;

    List<Long> findProductIds(Long shopCouponId);

    List<CereBuyerShopCoupon> findByIds(List<OrderProductParam> shops);
}
