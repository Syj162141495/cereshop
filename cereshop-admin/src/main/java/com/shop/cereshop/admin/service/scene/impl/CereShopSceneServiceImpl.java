/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.scene.impl;

import com.shop.cereshop.admin.dao.scene.CereShopSceneDAO;
import com.shop.cereshop.admin.service.scene.CereShopSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereShopSceneServiceImpl implements CereShopSceneService {

    @Autowired
    private CereShopSceneDAO cereShopSceneDAO;
}
