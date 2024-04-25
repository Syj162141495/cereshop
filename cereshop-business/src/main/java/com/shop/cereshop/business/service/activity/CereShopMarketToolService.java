/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity;

import com.shop.cereshop.business.page.activity.MarketTool;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopMarketToolService {
    void save(ToolSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ToolUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(ToolDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    void end(ToolEndParam param, CerePlatformBusiness user) throws CoBusinessException;

    MarketTool getById(Long toolId) throws CoBusinessException;

    List<MarketTool> getAll(ToolGetAllParam param) throws CoBusinessException;
}
