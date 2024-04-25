/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity.impl;

import com.shop.cereshop.admin.dao.activity.CerePlatformActivityDetailDAO;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityDetailService;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivityDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformActivityDetailServiceImpl implements CerePlatformActivityDetailService {

    @Autowired
    private CerePlatformActivityDetailDAO cerePlatformActivityDetailDAO;

    @Override
    public void insert(CerePlatformActivityDetail detail) throws CoBusinessException {
        cerePlatformActivityDetailDAO.insert(detail);
    }

    @Override
    public void deleteByActivityId(Long activityId) throws CoBusinessException {
        cerePlatformActivityDetailDAO.deleteByActivityId(activityId);
    }

    @Override
    public List<CerePlatformActivityDetail> findByActivityId(Long activityId) {
        return cerePlatformActivityDetailDAO.findByActivityId(activityId);
    }
}
