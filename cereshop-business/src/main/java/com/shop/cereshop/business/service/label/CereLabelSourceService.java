/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label;

import com.shop.cereshop.business.page.shop.LabelSource;
import com.shop.cereshop.business.param.label.LabelDeleteSourceParam;
import com.shop.cereshop.business.param.label.LabelGetSourceParam;
import com.shop.cereshop.business.param.label.LabelSaveSourceParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.label.CereLabelSource;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereLabelSourceService {
    List<CereLabelSource> findByLabelId(Long labelId);

    void deleteByLabelId(Long labelId) throws CoBusinessException;

    void insertBatch(List<CereLabelSource> list) throws CoBusinessException;

    void saveSource(LabelSaveSourceParam param, CerePlatformBusiness user) throws CoBusinessException;

    List<LabelSource> getAllByLabel(LabelGetSourceParam param) throws CoBusinessException;

    void deleteSource(LabelDeleteSourceParam param, CerePlatformBusiness user) throws CoBusinessException;

    List<CereLabelSource> findByLabelIdAndType(Long labelId,Integer labelType);

    void updateSource(LabelSaveSourceParam param, CerePlatformBusiness user) throws CoBusinessException;
}
