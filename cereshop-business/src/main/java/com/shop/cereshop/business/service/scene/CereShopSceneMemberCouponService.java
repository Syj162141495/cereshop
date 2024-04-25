/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.scene;

import com.shop.cereshop.business.page.tool.ShopCoupon;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMemberCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopSceneMemberCouponService {
    void insertBatch(List<CereShopSceneMemberCoupon> coupons) throws CoBusinessException;

    void deleteBySceneId(Long sceneId) throws CoBusinessException;

    List<ShopCoupon> findCoupons(Long memberLevelId,Long sceneId);
}
