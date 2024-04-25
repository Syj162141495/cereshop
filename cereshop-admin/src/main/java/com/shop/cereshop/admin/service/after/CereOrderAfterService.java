/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.after;

import com.shop.cereshop.admin.page.after.AfterBuyer;
import com.shop.cereshop.admin.page.after.AfterDetail;
import com.shop.cereshop.admin.page.after.AfterDilevery;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.after.AfterGetAllParam;
import com.shop.cereshop.admin.param.after.AfterGetDileveryParam;
import com.shop.cereshop.admin.param.after.AfterhandleParam;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereOrderAfterService {
    Page getAll(AfterGetAllParam param) throws CoBusinessException;

    AfterDetail getById(Long afterId) throws CoBusinessException,Exception;

    AfterBuyer getBuyer(Long buyerUserId) throws CoBusinessException;

    void handle(AfterhandleParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    List<AfterDilevery> getDilevery(AfterGetDileveryParam param) throws CoBusinessException,Exception;

    void insert(CereOrderAfter cereOrderAfter) throws CoBusinessException;
}
