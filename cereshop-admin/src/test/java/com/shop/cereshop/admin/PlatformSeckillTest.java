/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.param.seckill.SeckillSaveParam;
import com.shop.cereshop.admin.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 平台秒杀活动测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformSeckillTest {

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Test
    public void save(){
        try {
            CerePlatformUser user=new CerePlatformUser();
            user.setPlatformUserId(2l);
            SeckillSaveParam param=new SeckillSaveParam();
            param.setSeckillName("817秒杀");
            param.setSignStartTime("2021-08-14 12:00:00");
            param.setSignEndTime("2021-08-15 12:00:00");
            param.setStartTime("2021-08-16 12:00:00");
            param.setEndTime("2021-09-16 12:00:00");
            param.setIfBond(0);
            param.setSeckillMoney(new BigDecimal(50));
            param.setIfLimit(0);
            param.setIfAdd(0);
            cerePlatformSeckillService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
