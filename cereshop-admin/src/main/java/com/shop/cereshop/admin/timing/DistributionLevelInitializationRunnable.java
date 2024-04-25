/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.timing;

import com.shop.cereshop.admin.service.distributor.CereShopDistributionLevelService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户标签定时任务处理
 */
public class DistributionLevelInitializationRunnable implements Runnable{

    private CereShopDistributionLevelService cereShopDistributionLevelService;

    public DistributionLevelInitializationRunnable(CereShopDistributionLevelService cereShopDistributionLevelService){
        this.cereShopDistributionLevelService=cereShopDistributionLevelService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有商家
            List<Long> shops=cereShopDistributionLevelService.findAllShops();
            if(!EmptyUtils.isEmpty(shops)){
                shops.forEach(id -> {
                    //根据商家id查询所有设置了升级规则的分销员等级数据
                    List<CereShopDistributionLevel> list=cereShopDistributionLevelService.findAllByShopId(id);
                    if(!EmptyUtils.isEmpty(list)){
                        list.stream()
                                //轮循处理自动升级
                                .peek(a -> {
                                    try {
                                        automaticUpgrade(a,id);
                                    } catch (CoBusinessException e) {
                                        e.printStackTrace();
                                    }
                                });
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void automaticUpgrade(CereShopDistributionLevel distributionLevel,Long shopId) throws CoBusinessException {
        List<CereShopDistributor> results=new ArrayList<>();
        //查询所有分销员
        List<CereShopDistributor> list=cereShopDistributionLevelService.findAllDistributorByShopId(shopId);
        if(!EmptyUtils.isEmpty(list)){
            list.stream()
                    //校验是否满足累计分销金额条件
                    .peek(a -> checkMoney(a,results,distributionLevel))
                    //校验是否满足邀请下级人数条件
                    .peek(a -> checkInvitation(a,results,distributionLevel))
                    //校验是否满足客户满人数条件
                    .peek(a -> checkCustomer(a,results,distributionLevel));
            if(!EmptyUtils.isEmpty(results)){
                List<Long> ids = results.stream()
                        .map(CereShopDistributor::getDistributorId)
                        .collect(Collectors.toList());
                //批量更新分销员的等级
                cereShopDistributionLevelService.updateBatchDistributorLevel(ids,distributionLevel.getDistributorLevelId());
            }
        }
    }

    private List<CereShopDistributor> checkCustomer(CereShopDistributor a, List<CereShopDistributor> results, CereShopDistributionLevel distributionLevel) {
        if(IntegerEnum.YES.getCode().equals(distributionLevel.getIfCustomer())){
            //设置了客户满人数条件,查询该分销员当前客户人数
            int person=cereShopDistributionLevelService.findCustomerByDistributor(a.getDistributorId());
            if(person>=distributionLevel.getConditionCustomer()){
                //如果客户人数大于等于分销员等级设置的客户人数,需要更新等级
                results.add(a);
            }
        }
        return results;
    }

    private List<CereShopDistributor> checkInvitation(CereShopDistributor a, List<CereShopDistributor> results, CereShopDistributionLevel distributionLevel) {
        if(IntegerEnum.YES.getCode().equals(distributionLevel.getIfInvitation())){
            //设置了邀请下级满人数条件,查询该分销员当前邀请下级人数
            int person=cereShopDistributionLevelService.findInvitationByDistributor(a.getDistributorId());
            if(person>=distributionLevel.getConditionInvitation()){
                //如果邀请下级人数大于等于分销员等级设置的邀请下级满人数,需要更新等级
                results.add(a);
            }
        }
        return results;
    }

    private List<CereShopDistributor> checkMoney(CereShopDistributor a, List<CereShopDistributor> results,CereShopDistributionLevel distributionLevel) {
        if(IntegerEnum.YES.getCode().equals(distributionLevel.getIfMoney())){
            //设置了累计分销金额条件,查询该分销员当前分销金额
            BigDecimal decimal=cereShopDistributionLevelService.findMoneyByDistributor(a.getDistributorId());
            if(decimal.compareTo(distributionLevel.getConditionMoney())!=-1){
                //如果分销金额大于等于分销员等级设置的分销金额,需要更新等级
                results.add(a);
            }
        }
        return results;
    }
}
