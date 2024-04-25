/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.platformtool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.polite.PoliteActivityDetail;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformPoliteActivityDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CerePlatformPoliteActivity record);

    int insertSelective(CerePlatformPoliteActivity record);

    List<CerePlatformPoliteActivity> findByActivityId(@Param("activityId") Long activityId);

    void updateBatch(@Param("list") List<CerePlatformPoliteActivity> list);

    void insertBatch(@Param("list") List<CerePlatformPoliteActivity> list);

    void deleteByPoliteId(@Param("politeId") Long politeId);

    List<PoliteActivityDetail> findByPoliteId(@Param("politeId") Long politeId);
}
