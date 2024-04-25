/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.label;

import com.shop.cereshop.admin.param.buyer.BuyerSaveUserLabelParam;
import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereBuyerLabelService {
    void insert(CereBuyerLabel cereBuyerLabel) throws CoBusinessException;

    void deleteLabelUser(List<Long> ids) throws CoBusinessException;

    void insertBatch(List<CereBuyerLabel> collect);

    List<Long> findAlreadyByUser(BuyerSaveUserLabelParam param);
}
