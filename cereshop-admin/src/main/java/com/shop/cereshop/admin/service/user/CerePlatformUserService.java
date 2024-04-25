/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.user;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.user.PlatformUser;
import com.shop.cereshop.admin.param.user.*;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformUserService {
    CerePlatformUser findByUserName(String userName);

    CerePlatformUser findByPhone(String phone);

    void save(UserSaveParam userParam, CerePlatformUser user) throws CoBusinessException;

    void update(UserUpdateParam userParam, CerePlatformUser user) throws CoBusinessException;

    void delete(UserDeleteParam userParam, CerePlatformUser user) throws CoBusinessException;

    PlatformUser getById(Long platformUserId) throws CoBusinessException,Exception;

    Page getAll(UserGetAllParam userParam) throws CoBusinessException;

    CerePlatformUser findByToken(String token);

    void forgetPassword(UserForgetPasswordParam user, CerePlatformUser cerePlatformUser) throws CoBusinessException;

    void updateToken(CerePlatformUser cerePlatformUser) throws CoBusinessException;

    void updatePassword(UserForgetPasswordParam passwordParam, CerePlatformUser user) throws CoBusinessException;
}
