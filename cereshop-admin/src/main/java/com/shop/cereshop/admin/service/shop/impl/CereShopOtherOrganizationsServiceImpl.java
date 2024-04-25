/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop.impl;

import com.shop.cereshop.admin.dao.shop.CereShopOtherOrganizationsDAO;
import com.shop.cereshop.admin.service.shop.CereShopOtherOrganizationsService;
import com.shop.cereshop.commons.domain.shop.CereShopOtherOrganizations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereShopOtherOrganizationsServiceImpl implements CereShopOtherOrganizationsService {

    @Autowired
    private CereShopOtherOrganizationsDAO cereShopOtherOrganizationsDAO;

    @Override
    public CereShopOtherOrganizations findByShopId(Long shopId) {
        return cereShopOtherOrganizationsDAO.findByShopId(shopId);
    }
}
