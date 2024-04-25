/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label;

import com.shop.cereshop.business.page.label.ShopLabel;
import com.shop.cereshop.business.param.label.LabelDeleteParam;
import com.shop.cereshop.business.param.label.LabelGetAllParam;
import com.shop.cereshop.business.param.label.LabelSaveParam;
import com.shop.cereshop.business.param.label.LabelUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.label.CereShopLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopLabelService {
    void save(LabelSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(LabelUpdateParam label, CerePlatformBusiness user) throws CoBusinessException;

    void delete(LabelDeleteParam label, CerePlatformBusiness user) throws CoBusinessException;

    CereShopLabel getById(Long labelId) throws CoBusinessException;

    List<ShopLabel> getAll(LabelGetAllParam param) throws CoBusinessException;

    CereShopLabel findByShopIdNotGroup(Long shopId);

    void insert(CereShopLabel cereShopLabel) throws CoBusinessException;
}
