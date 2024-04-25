/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.scene.impl;

import com.shop.cereshop.business.dao.scene.CereShopSceneMemberCouponDAO;
import com.shop.cereshop.business.page.tool.ShopCoupon;
import com.shop.cereshop.business.service.scene.CereShopSceneMemberCouponService;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMemberCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopSceneMemberCouponServiceImpl implements CereShopSceneMemberCouponService {

    @Autowired
    private CereShopSceneMemberCouponDAO cereShopSceneMemberCouponDAO;

    @Override
    public void insertBatch(List<CereShopSceneMemberCoupon> coupons) throws CoBusinessException {
        cereShopSceneMemberCouponDAO.insertBatch(coupons);
    }

    @Override
    public void deleteBySceneId(Long sceneId) throws CoBusinessException {
        cereShopSceneMemberCouponDAO.deleteBySceneId(sceneId);
    }

    @Override
    public List<ShopCoupon> findCoupons(Long memberLevelId,Long sceneId) {
        return cereShopSceneMemberCouponDAO.findCoupons(memberLevelId,sceneId);
    }
}
