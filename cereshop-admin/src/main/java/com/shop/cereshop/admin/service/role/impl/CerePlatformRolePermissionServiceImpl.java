/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.role.impl;

import com.shop.cereshop.admin.dao.role.CerePlatformRolePermissionDAO;
import com.shop.cereshop.admin.service.role.CerePlatformRolePermissionService;
import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformRolePermissionServiceImpl implements CerePlatformRolePermissionService {

    @Autowired
    private CerePlatformRolePermissionDAO cerePlatformRolePermissionDAO;

    @Override
    public void deleteByRoleId(Long roleId) throws CoBusinessException {
        cerePlatformRolePermissionDAO.deleteByRoleId(roleId);
    }

    @Override
    public void insert(CerePlatformRolePermission cerePlatformRolePermission) throws CoBusinessException {
        cerePlatformRolePermissionDAO.insert(cerePlatformRolePermission);
    }

    @Override
    public void insertBatch(List<CerePlatformRolePermission> rolePermissions) throws CoBusinessException {
        cerePlatformRolePermissionDAO.insertBatch(rolePermissions);
    }

    @Override
    public void deleteByPermissionId(Long permissionId) throws CoBusinessException {
        cerePlatformRolePermissionDAO.deleteByPermissionId(permissionId);
    }
}
