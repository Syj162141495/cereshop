/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.price;

import com.shop.cereshop.commons.domain.tool.CerePriceRule;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePriceRuleService {
    void insertBatch(List<CerePriceRule> priceRules) throws CoBusinessException;

    void deleteByPriceId(Long priceId) throws CoBusinessException;

    List<CerePriceRule> findRules(Long priceId);
}
