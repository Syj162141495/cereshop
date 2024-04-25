/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.order;

import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereCollageOrderService {
    void update(CereCollageOrder cereCollageOrder) throws CoBusinessException;

    List<Long> findOrderIds(Long collageId);
}
