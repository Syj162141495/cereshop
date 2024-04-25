/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.price.impl;

import com.shop.cereshop.business.dao.price.CerePriceRuleDAO;
import com.shop.cereshop.business.service.price.CerePriceRuleService;
import com.shop.cereshop.commons.domain.tool.CerePriceRule;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePriceRuleServiceImpl implements CerePriceRuleService {

    @Autowired
    private CerePriceRuleDAO cerePriceRuleDAO;

    @Override
    public void insertBatch(List<CerePriceRule> priceRules) throws CoBusinessException {
        cerePriceRuleDAO.insertBatch(priceRules);
    }

    @Override
    public void deleteByPriceId(Long priceId) throws CoBusinessException {
        cerePriceRuleDAO.deleteByPriceId(priceId);
    }

    @Override
    public List<CerePriceRule> findRules(Long priceId) {
        return cerePriceRuleDAO.findRules(priceId);
    }
}
