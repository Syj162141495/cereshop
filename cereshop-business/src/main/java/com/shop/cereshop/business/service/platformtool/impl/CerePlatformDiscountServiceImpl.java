/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool.impl;

import com.shop.cereshop.business.dao.platformtool.CerePlatformDiscountDAO;
import com.shop.cereshop.business.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformDiscountServiceImpl implements CerePlatformDiscountService {

    @Autowired
    private CerePlatformDiscountDAO cerePlatformDiscountDAO;

    @Override
    public CanvasPlatformDiscount getMinDiscount() throws CoBusinessException {
        return cerePlatformDiscountDAO.getMinDiscount();
    }

    @Override
    public List<Long> checkPlatformDiscount(List<Long> ids, String startTime, String endTime, Long shopId) {
        return cerePlatformDiscountDAO.checkPlatformDiscount(ids,startTime,endTime,shopId);
    }

    @Override
    public List<CerePlatformDiscount> findPlatformDiscount() {
        return cerePlatformDiscountDAO.findPlatformDiscount();
    }

    @Override
    public void updatePlatformDiscountEndState(List<CerePlatformDiscount> platformDiscounts, String time) throws CoBusinessException {
        cerePlatformDiscountDAO.updatePlatformDiscountEndState(platformDiscounts,time);
    }
}
