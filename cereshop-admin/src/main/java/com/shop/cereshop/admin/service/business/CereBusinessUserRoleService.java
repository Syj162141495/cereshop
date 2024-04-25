/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.business;

import com.shop.cereshop.commons.domain.business.CereBusinessUserRole;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereBusinessUserRoleService {
    void insert(CereBusinessUserRole cereBusinessUserRole) throws CoBusinessException;
}
