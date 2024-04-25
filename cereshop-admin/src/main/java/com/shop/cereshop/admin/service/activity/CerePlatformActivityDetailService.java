/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity;

import com.shop.cereshop.commons.domain.activity.CerePlatformActivityDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformActivityDetailService {
    void deleteByActivityId(Long activityId) throws CoBusinessException;

    List<CerePlatformActivityDetail> findByActivityId(Long activityId);

    void insert(CerePlatformActivityDetail detail) throws CoBusinessException;
}
