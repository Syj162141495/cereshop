/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.dict;

import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformDictService {

    List<CerePlatformDict> getExpressSelect() throws CoBusinessException;

    List<CerePlatformDict> getSelect(String dictName) throws CoBusinessException;
}
