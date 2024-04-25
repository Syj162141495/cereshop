/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.platformtool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.polite.Polite;
import com.shop.cereshop.admin.page.polite.PoliteActivity;
import com.shop.cereshop.admin.page.polite.PoliteDataDetail;
import com.shop.cereshop.admin.page.polite.PoliteDetail;
import com.shop.cereshop.admin.param.polite.PoliteGetAllParam;
import com.shop.cereshop.admin.param.polite.PoliteSaveParam;
import com.shop.cereshop.admin.param.polite.PoliteUpdateParam;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformPoliteDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long politeId);

    int insert(CerePlatformPolite record);

    int insertSelective(CerePlatformPolite record);

    CerePlatformPolite selectByPrimaryKey(Long politeId);

    int updateByPrimaryKeySelective(CerePlatformPolite record);

    int updateByPrimaryKey(CerePlatformPolite record);

    List<CerePlatformPolite> checkTime(PoliteSaveParam param);

    List<CerePlatformPolite> checkUpdateTime(PoliteUpdateParam param);

    PoliteDetail getById(@Param("politeId") Long politeId);

    int findActivityNumber(@Param("politeId") Long politeId);

    Integer findNumber(@Param("politeId") Long politeId);

    Integer findUsers(@Param("politeId") Long politeId);

    List<PoliteDataDetail> findDataDetails(@Param("politeId") Long politeId);

    List<Polite> getAll(PoliteGetAllParam param);

    List<PoliteActivity> getAllActivity();
}
