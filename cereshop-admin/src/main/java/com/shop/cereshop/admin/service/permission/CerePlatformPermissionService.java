/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.permission;

import com.shop.cereshop.admin.page.permission.MenuButton;
import com.shop.cereshop.admin.page.permission.RolePermission;
import com.shop.cereshop.admin.param.permission.PermissionDeleteParam;
import com.shop.cereshop.admin.param.permission.PermissionGetAllParam;
import com.shop.cereshop.admin.param.permission.PermissionSaveParam;
import com.shop.cereshop.admin.param.permission.PermissionUpdateParam;
import com.shop.cereshop.admin.param.role.RoleDistributionParam;
import com.shop.cereshop.admin.param.user.UserBuildParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformPermissionService {
    void save(PermissionSaveParam param, CerePlatformUser user) throws CoBusinessException;

    void update(PermissionUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(PermissionDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    CerePlatformPermission getById(Long permissionId) throws CoBusinessException;

    Page getAll(PermissionGetAllParam param) throws CoBusinessException;

    List<MenuButton> getAllByUserId(UserBuildParam user) throws CoBusinessException;

    CerePlatformPermission findBySort(Integer sort,Long id);

    Integer getMaxSort() throws CoBusinessException;

    RolePermission getRolePermission(RoleDistributionParam param) throws CoBusinessException;

    List<MenuButton> findAllPermissionShop();

    void insert(CerePlatformPermission permission) throws CoBusinessException;

    Long getMaxId();

    void insertBatch(List<CerePlatformPermission> permissions) throws CoBusinessException;
}
