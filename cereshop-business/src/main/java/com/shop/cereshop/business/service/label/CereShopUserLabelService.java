/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label;

import com.shop.cereshop.business.param.label.UserLabelGetAllParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.CereShopUserLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopUserLabelService {
    void save(CereShopUserLabel label, CerePlatformBusiness user) throws CoBusinessException;

    void update(CereShopUserLabel label, CerePlatformBusiness user) throws CoBusinessException;

    void delete(List<Long> ids, CerePlatformBusiness user) throws CoBusinessException;

    CereShopUserLabel getById(Long labelId) throws CoBusinessException;

    Page getAll(UserLabelGetAllParam param) throws CoBusinessException;

    List<CereShopUserLabel> getLabels(Long shopId) throws CoBusinessException;
}
