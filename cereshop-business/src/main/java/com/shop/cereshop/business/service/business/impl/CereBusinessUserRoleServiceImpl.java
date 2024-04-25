/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.business.impl;

import com.shop.cereshop.business.dao.business.CereBusinessUserRoleDAO;
import com.shop.cereshop.business.page.role.PlatformUserRole;
import com.shop.cereshop.business.service.business.CereBusinessUserRoleService;
import com.shop.cereshop.commons.domain.business.CereBusinessUserRole;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereBusinessUserRoleServiceImpl implements CereBusinessUserRoleService {

    @Autowired
    private CereBusinessUserRoleDAO cereBusinessUserRoleDAO;

    @Override
    public void insert(CereBusinessUserRole businessUserRole) throws CoBusinessException {
        cereBusinessUserRoleDAO.insert(businessUserRole);
    }

    @Override
    public void deleteByUserId(Long businessUserId) throws CoBusinessException {
        cereBusinessUserRoleDAO.deleteByUserId(businessUserId);
    }

    @Override
    public List<PlatformUserRole> findRolesByUserId(Long businessUserId) {
        return cereBusinessUserRoleDAO.findRolesByUserId(businessUserId);
    }
}
