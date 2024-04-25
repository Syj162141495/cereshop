/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.role;

import com.shop.cereshop.admin.page.permission.RolePermission;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.role.*;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformRoleService {
    void save(RoleSaveParam param, CerePlatformUser user) throws CoBusinessException;

    void update(RoleUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(RoleDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    CerePlatformRole getById(Long roleId) throws CoBusinessException;

    Page getAll(RoleGetAllParam param) throws CoBusinessException;

    void distribution(RoleDistributionParam param,CerePlatformUser user) throws CoBusinessException;

    void insert(CerePlatformRole role) throws CoBusinessException;
}
