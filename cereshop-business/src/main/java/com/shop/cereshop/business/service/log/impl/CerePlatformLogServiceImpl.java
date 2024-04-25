/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.log.impl;

import com.shop.cereshop.business.dao.log.CerePlatformLogDAO;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.log.CerePlatformLog;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CerePlatformLogServiceImpl implements CerePlatformLogService {

    @Autowired
    private CerePlatformLogDAO cerePlatformLogDAO;

    @Override
    public void addLog(CerePlatformBusiness user, String module, String operation, String describe, Long id, String time) {
        CerePlatformLog log=new CerePlatformLog();
        log.setPlatformUserId(user.getBusinessUserId());
        log.setModule(module);
        log.setOperation(operation);
        log.setOperationDescribtion(describe);
        log.setOnly(String.valueOf(id));
        log.setCreateTime(time);
        cerePlatformLogDAO.insert(log);
    }

    @Override
    public void deleteSignActivityByOnly(Long activityId,Long userId) throws CoBusinessException {
        cerePlatformLogDAO.deleteSignActivityByOnly(activityId,userId);
    }
}
