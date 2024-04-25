/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.pay.impl;

import com.shop.cereshop.business.dao.pay.CerePayLogDAO;
import com.shop.cereshop.business.page.pay.PayLog;
import com.shop.cereshop.business.service.pay.CerePayLogService;
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
    public void update(CerePayLog payLog) throws CoBusinessException {
        cerePayLogDAO.updateByPrimaryKeySelective(payLog);
    }

    @Override
    public PayLog findByOrderFormid(String orderFormid) throws CoBusinessException {
        return cerePayLogDAO.findByOrderFormid(orderFormid);
    }

    @Override
    public PayLog findByOutRefundNo(String refundNo) {
        return cerePayLogDAO.findByOutRefundNo(refundNo);
    }

    @Override
    public PayLog findByTransactionId(String transactionId) {
        return cerePayLogDAO.findByTransactionId(transactionId);
    }
}
