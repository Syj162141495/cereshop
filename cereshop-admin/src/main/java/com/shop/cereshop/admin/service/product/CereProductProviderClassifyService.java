/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product;

import com.shop.cereshop.admin.page.product.ProductProviderClassify;
import com.shop.cereshop.admin.param.product.ClassDeleteParam;
import com.shop.cereshop.admin.param.product.ProductProviderClassifyParam;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereProductProviderClassifyService {
    void add(ProductProviderClassifyParam param, CerePlatformUser user) throws CoBusinessException;

    void update(ProductProviderClassifyParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(ClassDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    ProductProviderClassify getById(Long classifyId) throws CoBusinessException;

    List<ProductProviderClassify> getByPid(Long classifyPID) throws CoBusinessException;
}
