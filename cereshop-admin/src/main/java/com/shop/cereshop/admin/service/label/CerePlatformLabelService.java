/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.label;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.label.*;
import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.domain.label.PlatformLabel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformLabelService {
    Page getAll(LabelGetAllParam param) throws CoBusinessException;

    PlatformLabel getById(Long buyerLabelId) throws CoBusinessException;

    void save(LabelSaveParam label, CerePlatformUser user) throws CoBusinessException;

    void update(LabelUpdateParam label, CerePlatformUser user) throws CoBusinessException;

    void delete(LabelDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    List<PlatformLabel> findExcel(LabelExcelParam param) throws CoBusinessException;

    List<CerePlatformLabel> findAll();

    List<Long> findAllByDay(Integer consumptionDay);

    List<Long> findRangeDayBuyers(CerePlatformLabel label);

    List<Long> findFrequencyBuyes(CerePlatformLabel label);

    List<Long> findMoneyBuyers(CerePlatformLabel label);

    void insertBatchBuyerLabel(List<CereBuyerLabel> collect);

    List<Long> findAllBuyers(CerePlatformLabel label);
}
