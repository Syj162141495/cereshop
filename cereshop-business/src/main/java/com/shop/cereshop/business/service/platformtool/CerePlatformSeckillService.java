/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool;

import com.shop.cereshop.business.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.math.BigDecimal;
import java.util.List;

public interface CerePlatformSeckillService {
    List<CanvasPlatformSeckill> getPlatformSeckills(RenovationParam param) throws CoBusinessException;

    List<Long> checkPlatformSeckill(List<Long> details, String startTime, String endTime, Long shopId);

    List<CerePlatformSeckill> findPlatformSeckill();

    List<CereShopSeckill> findShopSeckill();

    void updatePlatformSeckillEndState(List<CerePlatformSeckill> platformSeckills,String time) throws CoBusinessException;

    BigDecimal findMinProductPrice(List<Long> productIs);
}
