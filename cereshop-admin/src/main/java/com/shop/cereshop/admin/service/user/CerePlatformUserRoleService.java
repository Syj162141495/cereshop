/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.user;

import com.shop.cereshop.admin.page.role.PlatformUserRole;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.user.CerePlatformUserRole;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformUserRoleService {
    void insert(CerePlatformUserRole cerePlatformUserRole) throws CoBusinessException;

    void deleteByUserId(Long platformUserId) throws CoBusinessException;

    List<PlatformUserRole> findRolesByUserId(Long platformUserId);
}
