/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.shop.CereShopRelationshipDAO;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.page.shop.ShopRelationship;
import com.shop.cereshop.business.param.ship.ShipGetAllParam;
import com.shop.cereshop.business.param.ship.ShipSaveParam;
import com.shop.cereshop.business.param.ship.ShipUpdateParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.shop.CereShopRelationshipService;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopRelationship;
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
public class CereShopRelationshipServiceImpl implements CereShopRelationshipService {

    @Autowired
    private CereShopRelationshipDAO cereShopRelationshipDAO;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShipSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopRelationship relationship=new CereShopRelationship();
        relationship.setShopId(param.getShopId());
        relationship.setBindValidity(param.getBindValidity());
        relationship.setIfDistributionRelationship(param.getIfDistributionRelationship());
        relationship.setIfRobbing(param.getIfRobbing());
        relationship.setRobbingDay(param.getRobbingDay());
        relationship.setValidityDay(param.getValidityDay());
        relationship.setIfInvitation(param.getIfInvitation());
        if(!EmptyUtils.isEmpty(param.getRobbingDay())&&!EmptyUtils.isEmpty(param.getValidityDay())){
            //校验保护期天数必须小于有效期天数
            if(param.getRobbingDay()>=param.getValidityDay()){
                throw new CoBusinessException(CoReturnFormat.RELATION_TIME_ERROR);
            }
        }
        //新增关系设置
        cereShopRelationshipDAO.insert(relationship);
        //新增日志
        cerePlatformLogService.addLog(user,"关系设置","商户端操作","添加关系设置",relationship.getShopId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShipUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        CereShopRelationship relationship=new CereShopRelationship();
        relationship.setShopId(param.getShopId());
        relationship.setBindValidity(param.getBindValidity());
        relationship.setIfDistributionRelationship(param.getIfDistributionRelationship());
        relationship.setIfRobbing(param.getIfRobbing());
        relationship.setRobbingDay(param.getRobbingDay());
        relationship.setValidityDay(param.getValidityDay());
        relationship.setIfInvitation(param.getIfInvitation());
        if(!EmptyUtils.isEmpty(param.getRobbingDay())&&!EmptyUtils.isEmpty(param.getValidityDay())){
            //校验保护期天数必须小于有效期天数
            if(param.getRobbingDay()>=param.getValidityDay()){
                throw new CoBusinessException(CoReturnFormat.RELATION_TIME_ERROR);
            }
        }
        String time = TimeUtils.yyMMddHHmmss();
        //修改关系设置
        cereShopRelationshipDAO.update(relationship);
        //新增日志
        cerePlatformLogService.addLog(user,"关系设置","商户端操作","修改关系设置",relationship.getShopId(),time);
    }

    @Override
    public Page getAll(ShipGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopRelationship> list=cereShopRelationshipDAO.getAll(param);
        PageInfo<ShopRelationship> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CereShopRelationship getById(Long shopId) throws CoBusinessException {
        return cereShopRelationshipDAO.getById(shopId);
    }
}
