/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor.impl;

import com.shop.cereshop.business.dao.distributor.CereShopDistributionLevelDAO;
import com.shop.cereshop.business.page.distribution.DistributoLevel;
import com.shop.cereshop.business.param.level.LevelDeleteParam;
import com.shop.cereshop.business.param.level.LevelSaveParam;
import com.shop.cereshop.business.param.level.LevelStateParam;
import com.shop.cereshop.business.param.level.LevelUpdateParam;
import com.shop.cereshop.business.service.distributor.CereShopDistributionLevelService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CereShopDistributionLevelServiceImpl implements CereShopDistributionLevelService {

    @Autowired
    private CereShopDistributionLevelDAO cereShopDistributionLevelDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public List<CereShopDistributionLevel> getAllLevel(Long shopId) throws CoBusinessException {
        return cereShopDistributionLevelDAO.getAllLevel(shopId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(LevelSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        //添加分销员等级
        String time = TimeUtils.yyMMddHHmmss();
        CereShopDistributionLevel level=new CereShopDistributionLevel();
        level.setConditionCustomer(param.getConditionCustomer());
        level.setConditionInvitation(param.getConditionInvitation());
        level.setConditionMoney(param.getConditionMoney());
        level.setDirectProportion(param.getDirectProportion());
        level.setIfSelf(param.getIfSelf());
        level.setIndirectProportion(param.getIndirectProportion());
        level.setLevelName(param.getLevelName());
        level.setLevelNum(param.getLevelNum());
        level.setIfCustomer(param.getIfCustomer());
        level.setIfInvitation(param.getIfInvitation());
        level.setIfMoney(param.getIfMoney());
        level.setShopId(param.getShopId());
        // 校验分销员等级是否满足条件
        checkLevelInfo(level, -1L, param.getShopId(), param.getLevelNum());
        level.setCreateTime(time);
        cereShopDistributionLevelDAO.insert(level);
        //新增日志
        cerePlatformLogService.addLog(user,"分销员等级管理","商户端操作","添加分销员等级",level.getDistributorLevelId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(LevelUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        //修改分销员等级
        String time = TimeUtils.yyMMddHHmmss();
        CereShopDistributionLevel level=new CereShopDistributionLevel();
        level.setDistributorLevelId(param.getDistributorLevelId());
        level.setConditionCustomer(param.getConditionCustomer());
        level.setConditionInvitation(param.getConditionInvitation());
        level.setConditionMoney(param.getConditionMoney());
        level.setDirectProportion(param.getDirectProportion());
        level.setIfSelf(param.getIfSelf());
        level.setIndirectProportion(param.getIndirectProportion());
        level.setLevelName(param.getLevelName());
        level.setLevelNum(param.getLevelNum());
        level.setIfCustomer(param.getIfCustomer());
        level.setIfInvitation(param.getIfInvitation());
        level.setIfMoney(param.getIfMoney());
        level.setShopId(param.getShopId());
        // 校验分销员等级信息是否满足条件
        checkLevelInfo(level, param.getDistributorLevelId(), param.getShopId(), param.getLevelNum());
        level.setUpdateTime(time);
        cereShopDistributionLevelDAO.updateByPrimaryKeySelective(level);
        //新增日志
        cerePlatformLogService.addLog(user,"分销员等级管理","商户端操作","修改分销员等级",level.getDistributorLevelId(),time);
    }

    /**
     * 校验分销员等级是否符合条件
     * @param level
     * @param distributorId
     * @param shopId
     * @param levelNum
     * @throws CoBusinessException
     */
    private void checkLevelInfo(CereShopDistributionLevel level, Long distributorId, Long shopId, Integer levelNum) throws CoBusinessException {
        CereShopDistributionLevel existLevel = cereShopDistributionLevelDAO.getByLevelNum(shopId, levelNum);
        if (existLevel != null && !existLevel.getDistributorLevelId().equals(distributorId)) {
            throw new CoBusinessException(CoReturnFormat.LEVEL_EXISTS);
        }
        //查询比当前分销员等级 小一级的数据
        CereShopDistributionLevel lowerLevel = cereShopDistributionLevelDAO.getLowerLevel(shopId, levelNum);
        if(lowerLevel!=null){
            //如果存在小一级的数据,校验当前修改后的规则是否大于等于小一级的数据
            if(IntegerEnum.YES.getCode().equals(level.getIfMoney())){
                //累计分销金额
                if(level.getConditionMoney().compareTo(lowerLevel.getConditionMoney()) < 0){
                    throw new CoBusinessException(CoReturnFormat.RULE_NOT_SMALLER);
                }
            }else if(IntegerEnum.YES.getCode().equals(level.getIfCustomer())){
                //累计邀请客户数
                if(level.getConditionCustomer().compareTo(lowerLevel.getConditionCustomer()) < 0){
                    throw new CoBusinessException(CoReturnFormat.RULE_NOT_SMALLER);
                }
            }else if(IntegerEnum.YES.getCode().equals(level.getIfInvitation())){
                //累计邀请下级数
                if(level.getConditionInvitation().compareTo(lowerLevel.getConditionInvitation()) < 0){
                    throw new CoBusinessException(CoReturnFormat.RULE_NOT_SMALLER);
                }
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(LevelDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopDistributionLevel level=new CereShopDistributionLevel();
        level.setDistributorLevelId(param.getDistributorLevelId());
        //删除分销员等级
        cereShopDistributionLevelDAO.deleteByPrimaryKey(level.getDistributorLevelId());
        //新增日志
        cerePlatformLogService.addLog(user,"分销员等级管理","商户端操作","删除分销员等级",level.getDistributorLevelId(),time);
    }

    @Override
    public DistributoLevel getById(Long distributorLevelId) throws CoBusinessException {
        DistributoLevel distributoLevel = cereShopDistributionLevelDAO.getById(distributorLevelId);
        List<Integer> coditions=new ArrayList<>();
        if(distributoLevel!=null){
            if(IntegerEnum.YES.getCode().equals(distributoLevel.getIfMoney())){
                coditions.add(1);
            }
            if(IntegerEnum.YES.getCode().equals(distributoLevel.getIfInvitation())){
                coditions.add(2);
            }
            if(IntegerEnum.YES.getCode().equals(distributoLevel.getIfCustomer())){
                coditions.add(3);
            }
            distributoLevel.setConditions(coditions);
        }
        return distributoLevel;
    }

    @Override
    public List<CereShopDistributionLevel> getAll(Long shopId) throws CoBusinessException {
        return cereShopDistributionLevelDAO.getAll(shopId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void updateSelf(LevelStateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        String describe="";
        if(IntegerEnum.YES.getCode().equals(param.getIfSelf())){
            describe="开启自购分佣";
        }else {
            describe="关闭自购分佣";
        }
        cereShopDistributionLevelDAO.updateIfSelf(user.getShopId(), param.getIfSelf(),time);
        //新增日志
        cerePlatformLogService.addLog(user,"分销员等级管理","商户端操作",describe,null,time);
    }
}
