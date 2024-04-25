/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.scene;

import com.shop.cereshop.business.page.scene.ShopSceneDetail;
import com.shop.cereshop.business.param.scene.SceneGetAllParam;
import com.shop.cereshop.business.param.scene.SceneGetByIdParam;
import com.shop.cereshop.business.param.scene.SceneSaveParam;
import com.shop.cereshop.business.param.scene.SceneUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.scene.CereShopScene;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopSceneService {
    void save(SceneSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(SceneUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(SceneGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    void start(SceneGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void stop(SceneGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopSceneDetail getById(Long sceneId) throws CoBusinessException;

    Page getAll(SceneGetAllParam param) throws CoBusinessException;

    CereShopScene findById(Long sceneId);

    void updateState(CereShopScene cereShopScene) throws CoBusinessException;

    List<CerePlatformMemberLevel> getMemberLevels() throws CoBusinessException;

    List<CereShopScene> findAll();
}
