/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity;

import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformActivityService {

    Page getCoupons(CanvasCouponParam param) throws CoBusinessException;

    List<CerePlatformActivity> findPlatformCoupon();

    void updateActivityEndState(List<CerePlatformActivity> activities, String time);
}
