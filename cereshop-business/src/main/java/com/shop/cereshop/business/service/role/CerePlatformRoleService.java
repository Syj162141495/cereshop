/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.role;

import com.shop.cereshop.business.param.permission.RoleDistributionParam;
import com.shop.cereshop.business.param.role.RoleDeleteParam;
import com.shop.cereshop.business.param.role.RoleGetAllParam;
import com.shop.cereshop.business.param.role.RoleSaveParam;
import com.shop.cereshop.business.param.role.RoleUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformRoleService {
    void save(RoleSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(RoleUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(RoleDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    CerePlatformRole getById(Long roleId) throws CoBusinessException;

    Page getAll(RoleGetAllParam param) throws CoBusinessException;

    void distribution(RoleDistributionParam param, CerePlatformBusiness user) throws CoBusinessException;
}
