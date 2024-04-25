/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.activity;

import com.shop.cereshop.commons.domain.activity.CerePlatformActivityDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformActivityDetailDAO extends BaseMapper<CerePlatformActivityDetail> {
    int insert(CerePlatformActivityDetail record);

    int insertSelective(CerePlatformActivityDetail record);

    void deleteByActivityId(@Param("activityId") Long activityId);

    List<CerePlatformActivityDetail> findByActivityId(@Param("activityId") Long activityId);
}
