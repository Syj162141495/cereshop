/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.param.activity.ActivitySaveParam;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityService;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 平台优惠券测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformActivityTest {

    @Autowired
    private CerePlatformActivityService cerePlatformActivityService;

    @Test
    public void save(){
        try {
            CerePlatformUser user=new CerePlatformUser();
            user.setPlatformUserId(2l);
            ActivitySaveParam param=new ActivitySaveParam();
            param.setActivityName("基础优惠");
            param.setDiscountMode(1);
            param.setThreshold(new BigDecimal(50));
            param.setCouponContent(new BigDecimal(10));
            param.setSignStartTime("2021-08-14 12:00:00");
            param.setSignEndTime("2021-08-15 12:00:00");
            param.setActivityStartTime("2021-08-16 12:00:00");
            param.setActivityEndTime("2021-09-16 12:00:00");
            param.setIfBond(0);
            param.setNumber(100);
            param.setReceiveType(2);
            param.setFrequency(5);
            cerePlatformActivityService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
