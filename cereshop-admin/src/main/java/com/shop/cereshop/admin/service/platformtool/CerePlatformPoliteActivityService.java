/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool;

import com.shop.cereshop.admin.page.polite.PoliteActivityDetail;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformPoliteActivityService {
    List<CerePlatformPoliteActivity> findByActivityId(Long activityId);

    void updateBatch(List<CerePlatformPoliteActivity> list) throws CoBusinessException;

    void insertBatch(List<CerePlatformPoliteActivity> details) throws CoBusinessException;

    void deleteByPoliteId(Long politeId) throws CoBusinessException;

    List<PoliteActivityDetail> findByPoliteId(Long politeId);
}
