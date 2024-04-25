/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.express.impl;

import com.shop.cereshop.admin.dao.express.CerePlatformExpressDAO;
import com.shop.cereshop.admin.param.express.ExpressUpdateParam;
import com.shop.cereshop.admin.service.express.CerePlatformExpressService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.express.CerePlatformExpress;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CerePlatformExpressServiceImpl implements CerePlatformExpressService {

    @Autowired
    private CerePlatformExpressDAO cerePlatformExpressDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ExpressUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformExpress express=new CerePlatformExpress();
        express.setExpressType(param.getExpressType());
        express.setPlatformUserId(user.getPlatformUserId());
        express.setUpdateTime(time);
        cerePlatformExpressDAO.update(express);
        //新增日志
        cerePlatformLogService.addLog(user,"物流查询策略","平台端操作","修改物流查询策略",String.valueOf(express.getPlatformUserId()),time);
    }
}
