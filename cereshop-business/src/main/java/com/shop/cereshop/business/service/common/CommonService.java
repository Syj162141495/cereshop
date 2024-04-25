/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.common;

import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CommonService {

    /**
     * 生成店铺编号
     * @return
     */
    String getShopCode();

    List<CerePlatformActivity> findPlatformCoupon();

    List<CerePlatformSeckill> findPlatformSeckill();

    List<CerePlatformDiscount> findPlatformDiscount();

    List<CerePlatformPolite> findPlatformPolite();

    List<CereShopGroupWork> findShopWorks();

    List<CereShopSeckill> findShopSeckill();

    List<CereShopDiscount> findShopDiscount();

    List<CereShopOperate> findShopOperate();

    void updateEndState(List<CerePlatformActivity> activities, List<CerePlatformSeckill> platformSeckills,
                        List<CerePlatformDiscount> platformDiscounts, List<CerePlatformPolite> platformPolites,
                        List<CereShopGroupWork> works, List<CereShopSeckill> seckills,
                        List<CereShopDiscount> discounts, List<CereShopOperate> operates) throws CoBusinessException;
}
