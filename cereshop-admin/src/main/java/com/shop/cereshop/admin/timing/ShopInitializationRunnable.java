/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.timing;

import com.shop.cereshop.admin.service.label.CerePlatformLabelService;
import com.shop.cereshop.admin.service.shop.CerePlatformShopService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 客户标签定时任务处理
 */
public class ShopInitializationRunnable implements Runnable{

    private CerePlatformShopService cerePlatformShopService;

    public ShopInitializationRunnable(CerePlatformShopService cerePlatformShopService){
        this.cerePlatformShopService=cerePlatformShopService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有可用商家数据
            List<CerePlatformShop> shops=cerePlatformShopService.findAll();
            String today = TimeUtils.yyMMddHHmmss();
            if(!EmptyUtils.isEmpty(shops)){
                for (CerePlatformShop shop : shops) {
                    //计算合同有效截止时间
                    String time = TimeUtils.getMoreYearAfter(shop.getEffectiveDate(), shop.getEffectiveYear());
                    //判断合同有效期是否过期
                    if(TimeUtils.compareTo(today,time+" 00:00:00")){
                        //如果当前时间大于有效截止时间,修改合同有效状态和店铺禁用
                        shop.setContractState(IntegerEnum.NO.getCode());
                        shop.setState(IntegerEnum.NO.getCode());
                        shop.setUpdateTime(today);
                        cerePlatformShopService.updateShopStop(shop);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
