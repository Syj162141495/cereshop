/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.product.ProductClassify;
import com.shop.cereshop.admin.param.product.ClassifyDeleteParam;
import com.shop.cereshop.admin.param.product.ClassifyGetAllParam;
import com.shop.cereshop.admin.param.product.ClassifyLevelParam;
import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereProductClassifyService {
    void save(ClassifyLevelParam param, CerePlatformUser user) throws CoBusinessException;

    void update(ClassifyLevelParam param, CerePlatformUser user) throws CoBusinessException;

    ProductClassify getById(Long classifyId) throws CoBusinessException;

    Page getAll(ClassifyGetAllParam param) throws CoBusinessException;

    void delete(ClassifyDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    List<Classify> getClassify() throws CoBusinessException;
}
