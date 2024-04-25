/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool;

import com.shop.cereshop.business.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformDiscountService {
    CanvasPlatformDiscount getMinDiscount() throws CoBusinessException;

    List<Long> checkPlatformDiscount(List<Long> details, String startTime, String endTime, Long shopId);

    List<CerePlatformDiscount> findPlatformDiscount();

    void updatePlatformDiscountEndState(List<CerePlatformDiscount> platformDiscounts, String time) throws CoBusinessException;
}
