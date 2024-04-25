/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CereShopReturnDAO;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.shop.CereShopReturnService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopReturn;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CereShopReturnServiceImpl implements CereShopReturnService {

    @Autowired
    private CereShopReturnDAO cereShopReturnDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public CereShopReturn findByShopId(Long shopId) {
        return cereShopReturnDAO.findByShopId(shopId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateReturn(CereShopReturn cereShopReturn, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        cereShopReturnDAO.update(cereShopReturn);
        //新增日志
        cerePlatformLogService.addLog(user,"店铺管理","商户端操作","修改店铺退货地址",cereShopReturn.getShopId(),time);
    }

    @Override
    public void update(CereShopReturn shopReturn) throws CoBusinessException {
        cereShopReturnDAO.update(shopReturn);
    }

    @Override
    public void insert(CereShopReturn shopReturn) throws CoBusinessException {
        cereShopReturnDAO.insert(shopReturn);
    }
}
