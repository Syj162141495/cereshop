/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.timing;

import com.shop.cereshop.business.service.product.CereShopProductService;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductDownRunnable implements Runnable{

    private CereShopProductService cereShopProductService;

    public ProductDownRunnable(CereShopProductService cereShopProductService) {
        this.cereShopProductService = cereShopProductService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有规格库存都为0的商品数据
            List<Long> ids=cereShopProductService.findAllZeroStockNumber();
            if(!EmptyUtils.isEmpty(ids)){
                //修改状态为已下架
                cereShopProductService.updateBatchShelveState(ids, TimeUtils.yyMMddHHmmss());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
