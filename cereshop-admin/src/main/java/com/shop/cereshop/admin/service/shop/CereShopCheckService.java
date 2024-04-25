/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.shopcheck.CheckDeleteParam;
import com.shop.cereshop.admin.param.shopcheck.CheckGetAllParam;
import com.shop.cereshop.admin.param.shopcheck.CheckHandleParam;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopCheckService {
    Page getAll(CheckGetAllParam param) throws CoBusinessException;

    Shop getById(Long shopId) throws CoBusinessException;

    void handle(CheckHandleParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void delete(CheckDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    void setInitPermission(Long shopId, String yyMMddHHmmss) throws CoBusinessException;
}
