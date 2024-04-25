/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.param.discount.DiscountSaveParam;
import com.shop.cereshop.admin.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 平台限时折扣活动测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformDiscountTest {

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Test
    public void save(){
        try {
            CerePlatformUser user=new CerePlatformUser();
            user.setPlatformUserId(2l);
            DiscountSaveParam param=new DiscountSaveParam();
            param.setDiscountName("818限时折扣");
            param.setSignStartTime("2021-08-30 21:56:00");
            param.setSignEndTime("2021-08-30 21:57:00");
            param.setStartTime("2021-08-30 21:58:00");
            param.setEndTime("2021-08-30 21:59:00");
            param.setIfBond(0);
            param.setDiscount(new BigDecimal(8));
            param.setIfLimit(0);
            param.setIfAdd(0);
            cerePlatformDiscountService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
