/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.compose;

import com.shop.cereshop.business.page.compose.ShopComposeDetail;
import com.shop.cereshop.business.param.compose.*;
import com.shop.cereshop.business.param.product.ProductGetAllParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopComposeService {
    void save(ComposeSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ComposeUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(ComposeGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    void start(ComposeStartParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopComposeDetail getById(Long composeId) throws CoBusinessException;

    Page getAll(ComposeGetAllParam param) throws CoBusinessException;

    CereShopCompose findById(Long composeId);

    void updateState(CereShopCompose cereShopCompose) throws CoBusinessException;

    Page selectProduct(ProductGetAllParam param) throws CoBusinessException;
}
