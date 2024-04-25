/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business;

import com.shop.cereshop.business.param.scene.SceneMemberParam;
import com.shop.cereshop.business.param.scene.SceneSaveParam;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 场景营销测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSceneTest {

    @Autowired
    private CereShopSceneService cereShopSceneService;

    @Test
    public void save(){
        try {
            CerePlatformBusiness user=new CerePlatformBusiness();
            user.setBusinessUserId(103l);
            user.setShopId(75l);
            SceneSaveParam param=new SceneSaveParam();
            param.setSceneType(1);
            param.setShopId(75l);
            param.setSceneName("节日营销");
            param.setStartTime("2021-08-30 22:37:00");
            param.setSceneRule(2);
            param.setEndTime("2021-08-30 22:38:00");
            List<SceneMemberParam> list=new ArrayList<>();
            SceneMemberParam sceneMemberParam1=new SceneMemberParam();
            sceneMemberParam1.setIds(null);
            sceneMemberParam1.setIfFreeShipping(1);
            sceneMemberParam1.setMemberLevelId(1l);
            sceneMemberParam1.setDiscount(new BigDecimal(9));
            list.add(sceneMemberParam1);
            SceneMemberParam sceneMemberParam2=new SceneMemberParam();
            sceneMemberParam2.setIds(null);
            sceneMemberParam2.setIfFreeShipping(0);
            sceneMemberParam2.setMemberLevelId(2l);
            sceneMemberParam2.setDiscount(new BigDecimal(8));
            list.add(sceneMemberParam2);
            param.setMemberParams(list);
            cereShopSceneService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
