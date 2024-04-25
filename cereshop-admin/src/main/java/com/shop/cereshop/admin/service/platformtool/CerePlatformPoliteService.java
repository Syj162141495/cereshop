/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool;

import com.shop.cereshop.admin.page.polite.PoliteData;
import com.shop.cereshop.admin.page.polite.PoliteDetail;
import com.shop.cereshop.admin.param.polite.PoliteGetAllParam;
import com.shop.cereshop.admin.param.polite.PoliteGetByIdParam;
import com.shop.cereshop.admin.param.polite.PoliteSaveParam;
import com.shop.cereshop.admin.param.polite.PoliteUpdateParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformPoliteService {
    void save(PoliteSaveParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    CerePlatformPolite findById(Long politeId);

    void updatePolite(CerePlatformPolite cerePlatformPolite) throws CoBusinessException;

    void update(PoliteUpdateParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void delete(PoliteGetByIdParam param, CerePlatformUser user) throws CoBusinessException;

    void stop(PoliteGetByIdParam param, CerePlatformUser user) throws CoBusinessException;

    PoliteDetail getById(Long politeId) throws CoBusinessException;

    PoliteData getData(PoliteGetByIdParam param) throws CoBusinessException;

    Page getAll(PoliteGetAllParam param) throws CoBusinessException;

    Page getAllActivity(PageParam param) throws CoBusinessException;
}
