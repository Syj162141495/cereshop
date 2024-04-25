/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.scene.impl;

import com.shop.cereshop.business.dao.scene.CereShopSceneMemberDAO;
import com.shop.cereshop.business.page.scene.SceneMember;
import com.shop.cereshop.business.service.scene.CereShopSceneMemberService;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMember;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopSceneMemberServiceImpl implements CereShopSceneMemberService {

    @Autowired
    private CereShopSceneMemberDAO cereShopSceneMemberDAO;

    @Override
    public void insertBatch(List<CereShopSceneMember> collect) throws CoBusinessException {
        cereShopSceneMemberDAO.insertBatch(collect);
    }

    @Override
    public void deleteBySceneId(Long sceneId) throws CoBusinessException {
        cereShopSceneMemberDAO.deleteBySceneId(sceneId);
    }

    @Override
    public List<SceneMember> findBySceneId(Long sceneId) {
        return cereShopSceneMemberDAO.findBySceneId(sceneId);
    }
}
