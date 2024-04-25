/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor;

import com.shop.cereshop.business.page.distribution.DistributoLevel;
import com.shop.cereshop.business.param.level.LevelDeleteParam;
import com.shop.cereshop.business.param.level.LevelSaveParam;
import com.shop.cereshop.business.param.level.LevelStateParam;
import com.shop.cereshop.business.param.level.LevelUpdateParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopDistributionLevelService {
    List<CereShopDistributionLevel> getAllLevel(Long shopId) throws CoBusinessException;

    void save(LevelSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(LevelUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(LevelDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    DistributoLevel getById(Long distributorLevelId) throws CoBusinessException;

    List<CereShopDistributionLevel> getAll(Long shopId) throws CoBusinessException;

    void updateSelf(LevelStateParam param, CerePlatformBusiness user) throws CoBusinessException;
}
