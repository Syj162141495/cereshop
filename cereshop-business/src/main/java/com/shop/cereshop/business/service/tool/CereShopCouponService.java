/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.tool.ShopCouponData;
import com.shop.cereshop.business.page.tool.ShopCouponDetail;
import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopCouponService {
    void save(ShopCouponSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ShopCouponUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    ShopCouponDetail getById(Long shopCouponId) throws CoBusinessException;

    Page getAll(ShopCouponGetAllParam param) throws CoBusinessException;

    void delete(Long shopCouponId, CerePlatformBusiness user) throws CoBusinessException;

    void stop(Long shopCouponId, CerePlatformBusiness user) throws CoBusinessException;

    Page getProducts(ToolProductParam param) throws CoBusinessException;

    List<OperateCoupon> selectCoupon(OperateCouponParam param) throws CoBusinessException;

    ShopCouponData getData(Long shopCouponId,Long shopId) throws CoBusinessException;

    CereShopCoupon findById(Long shopCouponId);

    void updateState(CereShopCoupon cereShopCoupon) throws CoBusinessException;

    void updateBuyerCouponState(Long shopCouponId) throws CoBusinessException;

    List<CereShopCoupon> findAllByShopId(Long shopId);

    Page getShopCoupons(CanvasCouponParam param) throws CoBusinessException;
}
