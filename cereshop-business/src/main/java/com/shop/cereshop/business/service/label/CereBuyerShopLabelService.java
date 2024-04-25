/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label;

import com.shop.cereshop.business.param.buyer.BuyerUserSaveParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.label.CereBuyerShopLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereBuyerShopLabelService {
    void saveLabel(BuyerUserSaveParam group, CerePlatformBusiness user) throws CoBusinessException;

    void insertBatch(List<CereBuyerShopLabel> collect) throws CoBusinessException;

    void deleteById(Long buyerUserId) throws CoBusinessException;

    List<Long> findByUserId(Long buyerUserId);
}
