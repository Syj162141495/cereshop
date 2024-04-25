/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.scene;

import com.shop.cereshop.business.page.scene.SceneMember;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMember;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopSceneMemberService {
    void insertBatch(List<CereShopSceneMember> collect) throws CoBusinessException;

    void deleteBySceneId(Long sceneId) throws CoBusinessException;

    List<SceneMember> findBySceneId(Long sceneId);
}
