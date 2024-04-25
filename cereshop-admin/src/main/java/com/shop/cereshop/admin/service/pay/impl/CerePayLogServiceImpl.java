/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.pay.impl;

import com.shop.cereshop.admin.dao.pay.CerePayLogDAO;
import com.shop.cereshop.admin.page.after.PayLog;
import com.shop.cereshop.admin.service.pay.CerePayLogService;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CerePayLogServiceImpl implements CerePayLogService {

    @Autowired
    private CerePayLogDAO cerePayLogDAO;

    @Override
    public void insert(CerePayLog payLog) throws CoBusinessException {
        cerePayLogDAO.insert(payLog);
    }

    @Override
    public PayLog findByOrderFormid(String orderFormid) {
        return cerePayLogDAO.findByOrderFormid(orderFormid);
    }

    @Override
    public void update(CerePayLog payLog) throws CoBusinessException {
        cerePayLogDAO.updateByPrimaryKeySelective(payLog);
    }
}
