/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.activity;

import com.shop.cereshop.commons.domain.activity.CereSignProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereSignProductDAO extends BaseMapper<CereSignProduct> {
    int insert(CereSignProduct record);

    int insertSelective(CereSignProduct record);

    void deleteById(@Param("signId") Long signId);

    List<CereSignProduct> selectSignProductList(@Param("signType") Integer signType, @Param("activityId") Long activityId);
}
