/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.sensitive;

import com.shop.cereshop.admin.param.sensitive.SensitiveSaveParam;
import com.shop.cereshop.admin.param.sensitive.SensitiveUpdateParam;
import com.shop.cereshop.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformSensitiveService {
    void save(SensitiveSaveParam param, CerePlatformUser user) throws CoBusinessException;

    void update(SensitiveUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    List<CerePlatformSensitive> get() throws CoBusinessException;

}
