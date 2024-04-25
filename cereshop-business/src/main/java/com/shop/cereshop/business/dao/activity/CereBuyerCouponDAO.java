/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.activity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.activity.CereBuyerCoupon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereBuyerCouponDAO extends BaseMapper<CereBuyerCoupon> {
    int insert(CereBuyerCoupon record);

    int insertSelective(CereBuyerCoupon record);

    void updateByUserIdAndCouponId(CereBuyerCoupon cereBuyerCoupon);
}
