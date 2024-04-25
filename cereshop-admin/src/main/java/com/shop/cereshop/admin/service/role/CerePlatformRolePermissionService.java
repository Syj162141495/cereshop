/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.role;

import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformRolePermissionService {
    void deleteByRoleId(Long roleId) throws CoBusinessException;

    void insert(CerePlatformRolePermission cerePlatformRolePermission) throws CoBusinessException;

    void insertBatch(List<CerePlatformRolePermission> rolePermissions) throws CoBusinessException;

    void deleteByPermissionId(Long permissionId) throws CoBusinessException;
}
