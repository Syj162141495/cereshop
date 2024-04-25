/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.timing;

import com.shop.cereshop.business.param.tool.CrowdCondition;
import com.shop.cereshop.business.service.product.CereShopProductService;
import com.shop.cereshop.business.service.tool.CereShopCrowdService;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateCrowdPersonRunnable implements Runnable{

    private CereShopCrowdService cereShopCrowdService;

    public UpdateCrowdPersonRunnable(CereShopCrowdService cereShopCrowdService) {
        this.cereShopCrowdService = cereShopCrowdService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            String time=TimeUtils.yyMMddHHmmss();
            //查询所有客户分群数据
            List<CereShopCrowd> list=cereShopCrowdService.findAll();
            if(!EmptyUtils.isEmpty(list)){
                List<CereShopCrowd> updates=new ArrayList<>();
                CrowdCondition condition=new CrowdCondition();
                for (CereShopCrowd crowd : list) {
                    //定义客户数据
                    List<String> ids=null;
                    if(!EmptyUtils.isEmpty(crowd.getShopBuyYes())){
                        //店铺有购买
                        condition.setType(1);
                        condition.setShopId(crowd.getShopId());
                        condition.setNumber(new BigDecimal(crowd.getShopBuyYes()));
                        ids=cereShopCrowdService.findUserByShopBuyCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopBuyNo())){
                        //店铺无购买
                        condition.setType(2);
                        condition.setShopId(crowd.getShopId());
                        ids=cereShopCrowdService.findUserByShopBuyCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无购买的客户id
                            ids=cereShopCrowdService.findNoBuy(condition);
                        }
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopOrderYes())){
                        //店铺有下单
                        condition.setType(3);
                        condition.setShopId(crowd.getShopId());
                        condition.setNumber(new BigDecimal(crowd.getShopOrderYes()));
                        ids=cereShopCrowdService.findUserByShopOrderCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopOrderNo())){
                        //店铺无下单
                        condition.setType(4);
                        condition.setShopId(crowd.getShopId());
                        ids=cereShopCrowdService.findUserByShopOrderCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无下单的客户id
                            ids=cereShopCrowdService.findNoOrder(condition);
                        }
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopAddYes())){
                        //店铺有加购
                        condition.setType(5);
                        condition.setShopId(crowd.getShopId());
                        ids=cereShopCrowdService.findUserByShopAddCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopAddNo())){
                        //店铺无加购
                        condition.setType(6);
                        condition.setShopId(crowd.getShopId());
                        ids=cereShopCrowdService.findUserByShopAddCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopVisitYes())){
                        //店铺有访问
                        condition.setType(7);
                        condition.setShopId(crowd.getShopId());
                        condition.setNumber(new BigDecimal(crowd.getShopVisitYes()));
                        ids=cereShopCrowdService.findUserByShopVisitCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getShopVisitNo())){
                        //店铺无访问
                        condition.setType(8);
                        condition.setShopId(crowd.getShopId());
                        ids=cereShopCrowdService.findUserByShopVisitCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无访问的客户id
                            ids=cereShopCrowdService.findNoVisit(condition);
                        }
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getEffectiveBuyCount())){
                        //有效购买次数
                        condition.setType(9);
                        condition.setShopId(crowd.getShopId());
                        condition.setCalculation(crowd.getEffectiveBuy());
                        condition.setNumber(new BigDecimal(crowd.getEffectiveBuyCount()));
                        ids=cereShopCrowdService.findUserByCountCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getEffectivePriceCount())){
                        //有效购买金额
                        condition.setType(10);
                        condition.setShopId(crowd.getShopId());
                        condition.setCalculation(crowd.getEffectivePrice());
                        condition.setNumber(crowd.getEffectivePriceCount());
                        ids=cereShopCrowdService.findUserByPriceCondition(condition);
                        condition.setIds(ids);
                    }
                    if(!EmptyUtils.isEmpty(crowd.getLabelId())){
                        //任意满足一个标签
                        String[] split = crowd.getLabelId().split(",");
                        List<Long> labelIds=new ArrayList<>();
                        for (String id : split) {
                            labelIds.add(Long.parseLong(id));
                        }
                        condition.setType(11);
                        condition.setShopId(crowd.getShopId());
                        condition.setLabelIds(labelIds);
                        ids=cereShopCrowdService.findUserByLabelCondition(condition);
                        condition.setIds(ids);
                    }
                    //更新客户数据
                    crowd.setIds(EmptyUtils.getImage(ids));
                    crowd.setUsers(ids.size());
                    crowd.setUpdateTime(time);
                    updates.add(crowd);
                }
                //批量更新分群数据
                cereShopCrowdService.updateBatch(updates);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
