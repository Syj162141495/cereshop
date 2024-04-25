/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.activity;

import com.shop.cereshop.commons.domain.activity.CereBuyerCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CereBuyerToolDAO extends BaseMapper<CereBuyerCoupon> {
    int insert(CereBuyerCoupon record);

    int insertSelective(CereBuyerCoupon record);

    Integer findUse(@Param("toolId") Long toolId);

    Integer findReceive(@Param("toolId") Long toolId);
}
