/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.express;

import com.shop.cereshop.admin.param.express.ExpressUpdateParam;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformExpressService {
    void update(ExpressUpdateParam param, CerePlatformUser user) throws CoBusinessException;
}
