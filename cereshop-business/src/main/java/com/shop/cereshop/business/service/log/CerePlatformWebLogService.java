/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.log;

import com.shop.cereshop.commons.domain.log.CerePlatformWebLog;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformWebLogService {
    void insert(CerePlatformWebLog webLog) throws CoBusinessException;
}
