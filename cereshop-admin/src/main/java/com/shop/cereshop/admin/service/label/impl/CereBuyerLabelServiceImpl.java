/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.label.impl;

import com.shop.cereshop.admin.dao.label.CereBuyerLabelDAO;
import com.shop.cereshop.admin.param.buyer.BuyerSaveUserLabelParam;
import com.shop.cereshop.admin.service.label.CereBuyerLabelService;
import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereBuyerLabelServiceImpl implements CereBuyerLabelService {

    @Autowired
    private CereBuyerLabelDAO cereBuyerLabelDAO;

    @Override
    public void insert(CereBuyerLabel cereBuyerLabel) throws CoBusinessException {
        cereBuyerLabelDAO.insert(cereBuyerLabel);
    }

    @Override
    public void deleteLabelUser(List<Long> ids) throws CoBusinessException {
        cereBuyerLabelDAO.deleteLabelUser(ids);
    }

    @Override
    public void insertBatch(List<CereBuyerLabel> collect) {
        cereBuyerLabelDAO.insertBatch(collect);
    }

    @Override
    public List<Long> findAlreadyByUser(BuyerSaveUserLabelParam param) {
        return cereBuyerLabelDAO.findAlreadyByUser(param);
    }
}
