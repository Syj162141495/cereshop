/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.log;

import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformLogService {
    void addLog(CerePlatformUser user, String module, String operation, String describe, String id, String time) throws CoBusinessException;
}
