/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.shop.cereshop.business.dao.activity.CereBuyerToolDAO;
import com.shop.cereshop.business.service.activity.CereBuyerToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CereBuyerToolServiceImpl implements CereBuyerToolService {

    @Autowired
    private CereBuyerToolDAO cereBuyerToolDAO;

    @Override
    public Integer findUse(Long toolId) {
        return cereBuyerToolDAO.findUse(toolId);
    }

    @Override
    public Integer findReceive(Long toolId) {
        return cereBuyerToolDAO.findReceive(toolId);
    }
}
