/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.distributor;

import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereDistributorBuyerService {
    void insert(CereDistributorBuyer distributorBuyer) throws CoBusinessException;
}
