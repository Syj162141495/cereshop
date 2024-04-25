/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.price;

import com.shop.cereshop.business.page.canvas.CanvasProductParam;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePriceProductService {
    void insertBatch(List<CerePriceProduct> priceProducts) throws CoBusinessException;

    void deleteByPriceId(Long priceId) throws CoBusinessException;

    List<ComposeProduct> findProducts(Long priceId);

    Page getPriceProducts(CanvasProductParam param) throws CoBusinessException;
}
