/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.business;

import com.shop.cereshop.business.page.shop.PlatformBusiness;
import com.shop.cereshop.business.page.user.Business;
import com.shop.cereshop.business.param.business.BusinessForgetParam;
import com.shop.cereshop.business.param.user.BusinessDeleteUser;
import com.shop.cereshop.business.param.user.BusinessGetAllUser;
import com.shop.cereshop.business.param.user.BusinessSaveUser;
import com.shop.cereshop.business.param.user.BusinessUpdateUser;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformBusinessService {
    PlatformBusiness findByUserName(String username) ;

    void forgetPassword(BusinessForgetParam user, CerePlatformBusiness cerePlatformBusiness) throws CoBusinessException;

    CerePlatformBusiness findByToken(String token);

    void insert(CerePlatformBusiness cerePlatformBusiness) throws CoBusinessException;

    void updateToken(CerePlatformBusiness cerePlatformBusiness) throws CoBusinessException;

    void save(BusinessSaveUser business, CerePlatformBusiness user) throws CoBusinessException;

    void update(BusinessUpdateUser business, CerePlatformBusiness user) throws CoBusinessException;

    Business getById(Long businessUserId) throws CoBusinessException,Exception;

    void delete(BusinessDeleteUser param, CerePlatformBusiness user) throws CoBusinessException;

    Page getAll(BusinessGetAllUser param) throws CoBusinessException;

    CerePlatformBusiness checkUserName(String shopPhone, Long id);

    void updatePassword(BusinessForgetParam passwordParam, CerePlatformBusiness user) throws CoBusinessException;

    void updateAvatar(CerePlatformBusiness user);
}
