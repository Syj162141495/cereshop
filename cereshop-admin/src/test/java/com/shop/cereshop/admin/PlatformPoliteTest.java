/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.param.polite.PoliteSaveParam;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台支付有礼活动测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformPoliteTest {

    @Autowired
    private CerePlatformPoliteService cerePlatformPoliteService;

    @Test
    public void save(){
        try {
            CerePlatformUser user=new CerePlatformUser();
            user.setPlatformUserId(2l);
            PoliteSaveParam param=new PoliteSaveParam();
            param.setPoliteName("819下单有礼");
            param.setStartTime("2021-08-16 12:00:00");
            param.setEndTime("2021-09-16 12:00:00");
            param.setBuyerMode(1);
            param.setBuyer(new BigDecimal(200));
            param.setGrowth(10);
            List<CerePlatformPoliteActivity> activities=new ArrayList<>();
            CerePlatformPoliteActivity activity=new CerePlatformPoliteActivity();
            activity.setActivityName("基础优惠");
            activity.setActivityType(1);
            activity.setActivityId(4l);
            activity.setThreshold(new BigDecimal(50));
            activity.setCouponContent(new BigDecimal(10));
            activities.add(activity);
            param.setDetails(activities);
            cerePlatformPoliteService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
