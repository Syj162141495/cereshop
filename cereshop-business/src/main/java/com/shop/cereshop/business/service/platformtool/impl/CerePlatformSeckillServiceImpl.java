/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool.impl;

import com.shop.cereshop.business.dao.platformtool.CerePlatformSeckillDAO;
import com.shop.cereshop.business.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CerePlatformSeckillServiceImpl implements CerePlatformSeckillService {

    @Autowired
    private CerePlatformSeckillDAO cerePlatformSeckillDAO;

    @Override
    public List<CanvasPlatformSeckill> getPlatformSeckills(RenovationParam param) throws CoBusinessException {
        return cerePlatformSeckillDAO.getPlatformSeckills(param);
    }

    @Override
    public List<Long> checkPlatformSeckill(List<Long> ids, String startTime, String endTime, Long shopId) {
        return cerePlatformSeckillDAO.checkPlatformSeckill(ids,startTime,endTime,shopId);
    }

    @Override
    public List<CerePlatformSeckill> findPlatformSeckill() {
        return cerePlatformSeckillDAO.findPlatformSeckill();
    }

    @Override
    public List<CereShopSeckill> findShopSeckill() {
        return cerePlatformSeckillDAO.findShopSeckill();
    }

    @Override
    public void updatePlatformSeckillEndState(List<CerePlatformSeckill> platformSeckills,String time) throws CoBusinessException {
        cerePlatformSeckillDAO.updatePlatformSeckillEndState(platformSeckills,time);
    }

    @Override
    public BigDecimal findMinProductPrice(List<Long> productIs) {
        return cerePlatformSeckillDAO.findMinProductPrice(productIs);
    }
}
