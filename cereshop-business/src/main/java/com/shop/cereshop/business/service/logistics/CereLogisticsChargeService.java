/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.logistics;

import com.shop.cereshop.business.page.logistics.Charge;
import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereLogisticsChargeService {
    void insertBatch(List<CereLogisticsCharge> charges) throws CoBusinessException;

    void deleteByLogisticsId(Long logisticsId) throws CoBusinessException;

    List<Charge> findByLogisticsId(Long logisticsId);
}
