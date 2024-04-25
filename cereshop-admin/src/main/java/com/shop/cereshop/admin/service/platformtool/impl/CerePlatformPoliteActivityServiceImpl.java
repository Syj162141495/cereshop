/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool.impl;

import com.shop.cereshop.admin.dao.platformtool.CerePlatformPoliteActivityDAO;
import com.shop.cereshop.admin.page.polite.PoliteActivityDetail;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteActivityService;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformPoliteActivityServiceImpl implements CerePlatformPoliteActivityService {

    @Autowired
    private CerePlatformPoliteActivityDAO cerePlatformPoliteActivityDAO;

    @Override
    public List<CerePlatformPoliteActivity> findByActivityId(Long activityId) {
        return cerePlatformPoliteActivityDAO.findByActivityId(activityId);
    }

    @Override
    public void updateBatch(List<CerePlatformPoliteActivity> list) throws CoBusinessException {
        cerePlatformPoliteActivityDAO.updateBatch(list);
    }

    @Override
    public void insertBatch(List<CerePlatformPoliteActivity> list) throws CoBusinessException {
        cerePlatformPoliteActivityDAO.insertBatch(list);
    }

    @Override
    public void deleteByPoliteId(Long politeId) throws CoBusinessException {
        cerePlatformPoliteActivityDAO.deleteByPoliteId(politeId);
    }

    @Override
    public List<PoliteActivityDetail> findByPoliteId(Long politeId) {
        return cerePlatformPoliteActivityDAO.findByPoliteId(politeId);
    }
}
