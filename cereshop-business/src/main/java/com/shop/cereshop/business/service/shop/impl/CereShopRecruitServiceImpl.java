/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CereShopRecruitDAO;
import com.shop.cereshop.business.param.recruit.ShopRecruitParam;
import com.shop.cereshop.business.service.distributor.CereShopDistributorService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.shop.CereShopRecruitService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.domain.shop.CereShopRecruit;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CereShopRecruitServiceImpl implements CereShopRecruitService {

    @Autowired
    private CereShopRecruitDAO cereShopRecruitDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereShopDistributorService cereShopDistributorService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopRecruitParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopRecruit recruit=new CereShopRecruit();
        recruit.setShopId(param.getShopId());
        recruit.setConsumptionMoney(param.getConsumptionMoney());
        recruit.setFrequency(param.getFrequency());
        recruit.setIfExamine(param.getIfExamine());
        recruit.setMoney(param.getMoney());
        recruit.setOrderFrequency(param.getOrderFrequency());
        recruit.setPurchaseEverything(param.getPurchaseEverything());
        recruit.setUrl(param.getUrl());
        //新增招募设置
        cereShopRecruitDAO.insert(recruit);
        //新增日志
        cerePlatformLogService.addLog(user,"招募设置","商户端操作","添加招募设置",recruit.getShopId(),time);
    }

    @Override
    public void update(ShopRecruitParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopRecruit recruit=new CereShopRecruit();
        recruit.setShopId(param.getShopId());
        recruit.setConsumptionMoney(param.getConsumptionMoney());
        recruit.setFrequency(param.getFrequency());
        recruit.setIfExamine(param.getIfExamine());
        recruit.setMoney(param.getMoney());
        recruit.setOrderFrequency(param.getOrderFrequency());
        recruit.setPurchaseEverything(param.getPurchaseEverything());
        recruit.setUrl(param.getUrl());
        cereShopRecruitDAO.update(recruit);
        if(IntegerEnum.NO.getCode().equals(param.getIfExamine())){
            //如果不需要审核,修改待审核分销员为审核通过
            List<CereShopDistributor> list=cereShopRecruitDAO.findStayDistributor(param.getShopId());
            if(!EmptyUtils.isEmpty(list)){
                list.forEach(cereShopDistributor -> {
                    cereShopDistributor.setState(IntegerEnum.YES.getCode());
                    cereShopDistributor.setJoinTime(time);
                    cereShopDistributor.setUpdateTime(time);
                });
                cereShopDistributorService.updateBatch(list);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"招募设置","商户端操作","修改招募设置",recruit.getShopId(),time);
    }

    @Override
    public ShopRecruitParam getByShopId(Long shopId) throws CoBusinessException {
        return cereShopRecruitDAO.getByShopId(shopId);
    }
}
