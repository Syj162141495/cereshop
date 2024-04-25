/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.user.impl;

import com.shop.cereshop.admin.dao.user.CerePlatformUserRoleDAO;
import com.shop.cereshop.admin.page.role.PlatformUserRole;
import com.shop.cereshop.admin.service.user.CerePlatformUserRoleService;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.user.CerePlatformUserRole;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformUserRoleServiceImpl implements CerePlatformUserRoleService {

    @Autowired
    private CerePlatformUserRoleDAO cerePlatformUserRoleDAO;

    @Override
    public void insert(CerePlatformUserRole cerePlatformUserRole) throws CoBusinessException {
        cerePlatformUserRoleDAO.insert(cerePlatformUserRole);
    }

    @Override
    public void deleteByUserId(Long platformUserId) throws CoBusinessException {
        cerePlatformUserRoleDAO.deleteByUserId(platformUserId);
    }

    @Override
    public List<PlatformUserRole> findRolesByUserId(Long platformUserId) {
        return cerePlatformUserRoleDAO.findRolesByUserId(platformUserId);
    }
}
