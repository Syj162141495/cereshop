/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.timing;

import com.shop.cereshop.business.service.common.CommonService;
import com.shop.cereshop.business.service.product.CereShopProductService;
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
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ToolRunnable implements Runnable{

    private CommonService commonService;

    public ToolRunnable(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有结束时间小于当前时间的平台优惠券数据
            List<CerePlatformActivity> activities=commonService.findPlatformCoupon();
            //查询所有结束时间小于当前时间的平台秒杀数据
            List<CerePlatformSeckill> platformSeckills=commonService.findPlatformSeckill();
            //查询所有结束时间小于当前时间的平台限时折扣数据
            List<CerePlatformDiscount> platformDiscounts=commonService.findPlatformDiscount();
            //查询所有结束时间小于当前时间的支付有礼活动数据
            List<CerePlatformPolite> platformPolites=commonService.findPlatformPolite();
            //查询所有结束时间小于当前时间的商家拼团数据
            List<CereShopGroupWork> works=commonService.findShopWorks();
            //查询所有结束时间小于当前时间的商家秒杀数据
            List<CereShopSeckill> seckills=commonService.findShopSeckill();
            //查询所有结束时间小于当前时间的商家限时折扣数据
            List<CereShopDiscount> discounts=commonService.findShopDiscount();
            //查询所有结束时间小于当前时间的商家运营计划数据
            List<CereShopOperate> operates=commonService.findShopOperate();
            //批量更新状态为已结束
            commonService.updateEndState(activities,platformSeckills,platformDiscounts,platformPolites,works,seckills,discounts,operates);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
