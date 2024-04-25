/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.timing;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.tool.ShopCrowdDetail;
import com.shop.cereshop.business.param.tool.CrowdCondition;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.business.service.tool.CereShopOperateService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.scene.CereShopScene;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 场景营销活动状态定时任务
 */
public class UpdateSceneStateRunnable implements Runnable{

    private CereShopSceneService cereShopSceneService;

    public UpdateSceneStateRunnable(CereShopSceneService cereShopSceneService){
        this.cereShopSceneService=cereShopSceneService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有场景营销活动
            List<CereShopScene> list= cereShopSceneService.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
