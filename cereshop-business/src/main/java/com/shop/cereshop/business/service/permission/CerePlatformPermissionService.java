/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.permission;

import com.shop.cereshop.business.page.permission.MenuButton;
import com.shop.cereshop.business.page.permission.RolePermission;
import com.shop.cereshop.business.param.permission.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformPermissionService {
    void save(PermissionSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(PermissionUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(PermissionDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    CerePlatformPermission getById(Long permissionId) throws CoBusinessException;

    Page getAll(PermissionGetAllParam param) throws CoBusinessException;

    List<MenuButton> getAllByUserId(UserBuildParam user) throws CoBusinessException;

    CerePlatformPermission findBySort(Integer sort,Long id,Long shopId);

    Integer getMaxSort() throws CoBusinessException;

    RolePermission getRolePermission(RoleDistributionParam param) throws CoBusinessException;

    int findMaxSort();

    void insert(CerePlatformPermission permission) throws CoBusinessException;
}
