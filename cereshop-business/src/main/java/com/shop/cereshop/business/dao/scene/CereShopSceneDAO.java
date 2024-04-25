/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.scene;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.scene.ShopScene;
import com.shop.cereshop.business.page.scene.ShopSceneDetail;
import com.shop.cereshop.business.param.scene.SceneGetAllParam;
import com.shop.cereshop.business.param.scene.SceneSaveParam;
import com.shop.cereshop.business.param.scene.SceneUpdateParam;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.scene.CereShopScene;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopSceneDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long sceneId);

    int insert(CereShopScene record);

    int insertSelective(CereShopScene record);

    CereShopScene selectByPrimaryKey(Long sceneId);

    int updateByPrimaryKeySelective(CereShopScene record);

    int updateByPrimaryKey(CereShopScene record);

    List<CereShopScene> checkTime(SceneSaveParam param);

    CereShopScene checkScene(@Param("sceneId") Long sceneId,@Param("shopId") Long shopId);

    List<CereShopScene> checkUpdateTime(SceneUpdateParam param);

    ShopSceneDetail getById(@Param("sceneId") Long sceneId);

    List<ShopScene> getAll(SceneGetAllParam param);

    List<CerePlatformMemberLevel> getMemberLevels();

    List<CereShopScene> findAll();
}
