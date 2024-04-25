/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business;

import com.shop.cereshop.business.param.compose.ComposeSaveParam;
import com.shop.cereshop.business.service.compose.CereShopComposeService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 组合捆绑试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopComposeTest {

    @Autowired
    private CereShopComposeService cereShopComposeService;

    @Test
    public void save(){
        try {
            CerePlatformBusiness user=new CerePlatformBusiness();
            user.setBusinessUserId(103l);
            ComposeSaveParam param=new ComposeSaveParam();
            param.setShopId(75l);
            param.setComposeName("组合捆绑1号");
            param.setStartTime("2021-08-16 12:00:00");
            param.setEndTime("2021-09-16 12:00:00");
            param.setComposeType(1);
            param.setPromote(new BigDecimal(153));
            List<CereComposeProduct> list=new ArrayList<>();
            CereComposeProduct CereComposeProduct1=new CereComposeProduct();
            CereComposeProduct1.setProductId(196l);
            list.add(CereComposeProduct1);
            CereComposeProduct CereComposeProduct2=new CereComposeProduct();
            CereComposeProduct2.setProductId(197l);
            list.add(CereComposeProduct2);
            CereComposeProduct CereComposeProduct3=new CereComposeProduct();
            CereComposeProduct3.setProductId(198l);
            list.add(CereComposeProduct3);
            param.setComposeProducts(list);
            cereShopComposeService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
