/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool;

import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformPoliteService {
    List<CerePlatformPolite> findPlatformPolite();

    void updatePoliteEndState(List<CerePlatformPolite> platformPolites, String time) throws CoBusinessException;
}
