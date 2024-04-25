/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.common.impl;

import com.shop.cereshop.business.service.activity.CerePlatformActivityService;
import com.shop.cereshop.business.service.common.CommonService;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.tool.CereShopDiscountService;
import com.shop.cereshop.business.service.tool.CereShopGroupWorkService;
import com.shop.cereshop.business.service.tool.CereShopOperateService;
import com.shop.cereshop.business.service.tool.CereShopSeckillService;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CerePlatformActivityService cerePlatformActivityService;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Autowired
    private CereShopGroupWorkService cereShopGroupWorkService;

    @Autowired
    private CereShopSeckillService cereShopSeckillService;

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    @Autowired
    private CereShopOperateService cereShopOperateService;

    @Autowired
    private CerePlatformPoliteService cerePlatformPoliteService;

    /**
     * 生成店铺编号
     * @return
     */
    @Override
    public String getShopCode() {
        return "SJ"+ RandomStringUtil.getRandomCode(8,0);
    }

    @Override
    public List<CerePlatformActivity> findPlatformCoupon() {
        return cerePlatformActivityService.findPlatformCoupon();
    }

    @Override
    public List<CerePlatformSeckill> findPlatformSeckill() {
        return cerePlatformSeckillService.findPlatformSeckill();
    }

    @Override
    public List<CerePlatformDiscount> findPlatformDiscount() {
        return cerePlatformDiscountService.findPlatformDiscount();
    }

    @Override
    public List<CerePlatformPolite> findPlatformPolite() {
        return cerePlatformPoliteService.findPlatformPolite();
    }

    @Override
    public List<CereShopGroupWork> findShopWorks() {
        return cereShopGroupWorkService.findShopWorks();
    }

    @Override
    public List<CereShopSeckill> findShopSeckill() {
        return cerePlatformSeckillService.findShopSeckill();
    }

    @Override
    public List<CereShopDiscount> findShopDiscount() {
        return cereShopDiscountService.findShopDiscount();
    }

    @Override
    public List<CereShopOperate> findShopOperate() {
        return cereShopOperateService.findShopOperate();
    }

    @Override
    public void updateEndState(List<CerePlatformActivity> activities, List<CerePlatformSeckill> platformSeckills,
                               List<CerePlatformDiscount> platformDiscounts, List<CerePlatformPolite> platformPolites,
                               List<CereShopGroupWork> works, List<CereShopSeckill> seckills,
                               List<CereShopDiscount> discounts, List<CereShopOperate> operates) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(activities)){
            cerePlatformActivityService.updateActivityEndState(activities,time);
        }
        if(!EmptyUtils.isEmpty(platformSeckills)){
            cerePlatformSeckillService.updatePlatformSeckillEndState(platformSeckills,time);
        }
        if(!EmptyUtils.isEmpty(platformDiscounts)){
            cerePlatformDiscountService.updatePlatformDiscountEndState(platformDiscounts,time);
        }
        if(!EmptyUtils.isEmpty(platformPolites)){
            cerePlatformPoliteService.updatePoliteEndState(platformPolites,time);
        }
        if(!EmptyUtils.isEmpty(works)){
            cereShopGroupWorkService.updateWorkEndState(works,time);
        }
        if(!EmptyUtils.isEmpty(seckills)){
            cereShopSeckillService.updateSeckillEndState(seckills,time);
        }
        if(!EmptyUtils.isEmpty(discounts)){
            cereShopDiscountService.updateDiscountEndState(discounts,time);
        }
        if(!EmptyUtils.isEmpty(operates)){
            cereShopOperateService.updateOperateEndState(operates,time);
        }
    }
}
