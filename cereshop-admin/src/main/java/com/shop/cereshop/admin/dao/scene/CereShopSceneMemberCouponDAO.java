/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.scene;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMemberCoupon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereShopSceneMemberCouponDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CereShopSceneMemberCoupon record);

    int insertSelective(CereShopSceneMemberCoupon record);
}
