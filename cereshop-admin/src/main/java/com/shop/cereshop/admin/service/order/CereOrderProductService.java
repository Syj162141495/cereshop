/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.order;

import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.order.CereOrderProductAttribute;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereOrderProductService {
    void insert(CereOrderProduct orderProduct) throws CoBusinessException;

    void insertBatchAttibutes(List<CereOrderProductAttribute> attributes) throws CoBusinessException;
}
