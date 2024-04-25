/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.order;

import com.shop.cereshop.business.param.after.AfterIdParam;
import com.shop.cereshop.business.param.order.OrderDileveryParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereOrderDileverService {
    void dilevery(OrderDileveryParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void refundDilevery(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;
}
