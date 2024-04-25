/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business;

import com.shop.cereshop.business.param.price.PriceSaveParam;
import com.shop.cereshop.business.service.price.CereShopPriceService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import com.shop.cereshop.commons.domain.tool.CerePriceRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 定价捆绑试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopPriceTest {

    @Autowired
    private CereShopPriceService cereShopPriceService;

    @Test
    public void save(){
        try {
            CerePlatformBusiness user=new CerePlatformBusiness();
            user.setBusinessUserId(103l);
            PriceSaveParam param=new PriceSaveParam();
            param.setShopId(75l);
            param.setComposeName("定价捆绑1号");
            param.setStartTime("2021-08-16 12:00:00");
            param.setEndTime("2021-09-16 12:00:00");
            List<CerePriceProduct> list=new ArrayList<>();
            CerePriceProduct priceProduct1=new CerePriceProduct();
            priceProduct1.setProductId(203l);
            list.add(priceProduct1);
            CerePriceProduct priceProduct2=new CerePriceProduct();
            priceProduct2.setProductId(204l);
            list.add(priceProduct2);
            CerePriceProduct priceProduct3=new CerePriceProduct();
            priceProduct3.setProductId(205l);
            list.add(priceProduct3);
            param.setPriceProducts(list);
            List<CerePriceRule> rules=new ArrayList<>();
            CerePriceRule cerePriceRule1=new CerePriceRule();
            cerePriceRule1.setNumber(2);
            cerePriceRule1.setPrice(new BigDecimal(89));
            rules.add(cerePriceRule1);
            CerePriceRule cerePriceRule2=new CerePriceRule();
            cerePriceRule2.setNumber(3);
            cerePriceRule2.setPrice(new BigDecimal(156));
            rules.add(cerePriceRule2);
            param.setPriceRules(rules);
            cereShopPriceService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
