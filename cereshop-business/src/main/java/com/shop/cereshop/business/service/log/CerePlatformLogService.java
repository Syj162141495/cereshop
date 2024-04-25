/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.log;

import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformLogService {
    void addLog(CerePlatformBusiness user, String module, String operation, String describe, Long id, String time);

    void deleteSignActivityByOnly(Long activityId,Long userId) throws CoBusinessException;
}
