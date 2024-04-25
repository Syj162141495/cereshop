/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool.impl;

import com.shop.cereshop.business.dao.platformtool.CerePlatformPoliteDAO;
import com.shop.cereshop.business.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformPoliteServiceImpl implements CerePlatformPoliteService {

    @Autowired
    private CerePlatformPoliteDAO cerePlatformPoliteDAO;

    @Override
    public List<CerePlatformPolite> findPlatformPolite() {
        return cerePlatformPoliteDAO.findPlatformPolite();
    }

    @Override
    public void updatePoliteEndState(List<CerePlatformPolite> platformPolites, String time) throws CoBusinessException {
        cerePlatformPoliteDAO.updatePoliteEndState(platformPolites,time);
    }
}
