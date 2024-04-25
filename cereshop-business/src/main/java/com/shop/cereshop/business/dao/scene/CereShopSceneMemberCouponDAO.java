/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.scene;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.tool.ShopCoupon;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMemberCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopSceneMemberCouponDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CereShopSceneMemberCoupon record);

    int insertSelective(CereShopSceneMemberCoupon record);

    void insertBatch(@Param("list") List<CereShopSceneMemberCoupon> list);

    void deleteBySceneId(@Param("sceneId") Long sceneId);

    List<ShopCoupon> findCoupons(@Param("memberLevelId") Long memberLevelId,@Param("sceneId") Long sceneId);
}
