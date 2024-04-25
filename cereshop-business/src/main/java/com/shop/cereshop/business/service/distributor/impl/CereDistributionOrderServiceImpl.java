/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.distributor.CereDistributionOrderDAO;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.page.distribution.Achievement;
import com.shop.cereshop.business.page.distribution.AchievementDetai;
import com.shop.cereshop.business.page.distribution.DistributionOrder;
import com.shop.cereshop.business.param.distributor.DistributorGetAllAchievementParam;
import com.shop.cereshop.business.param.distributorOrder.OrderGetAllParam;
import com.shop.cereshop.business.param.distributorOrder.OrderSettlementParam;
import com.shop.cereshop.business.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereDistributionOrder;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CereDistributionOrderServiceImpl implements CereDistributionOrderService {

    @Autowired
    private CereDistributionOrderDAO cereDistributionOrderDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public Page getAll(OrderGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<DistributionOrder> list=cereDistributionOrderDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            //查询所有间接佣金数据
            List<CereDistributionOrder> childs=cereDistributionOrderDAO.findChilds(param);
            if(!EmptyUtils.isEmpty(childs)){
                //将间接佣金数据放到map中
                Map<Long,CereDistributionOrder> map=childs.stream()
                        .collect(Collectors.toMap(CereDistributionOrder::getOrderId, Function.identity()));
                list.forEach((cereDistributionOrder -> {
                    //获取间接佣金数据绑定到直接佣金数据中
                    cereDistributionOrder.setChild(map.get(cereDistributionOrder.getOrderId()));
                }));
            }
        }
        PageInfo<DistributionOrder> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void settlement(OrderSettlementParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //批量更新分销员订单结算状态
        if(!EmptyUtils.isEmpty(param.getIds())){
            cereDistributionOrderDAO.updateStatByIds(param.getIds());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"分销员订单管理","商户端操作","标记结算",null,time);
    }

    @Override
    public Page getAllAchievement(DistributorGetAllAchievementParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        //查询所有分销员数据
        List<Achievement> list=cereDistributionOrderDAO.getAllAchievement(param);
        PageInfo<Achievement> pageInfo=new PageInfo<>(list);
        if(!EmptyUtils.isEmpty(list)){
            list=list.stream()
                    //查询订单数
                    .peek(a->a.setOrders(cereDistributionOrderDAO.findOrders(a.getDistributorId())))
                    //查询成交金额
                    .peek(a->a.setDealMoney(cereDistributionOrderDAO.findDealMoney(a.getDistributorId())))
                    //查询总佣金
                    .peek(a->a.setCommissionMoney(cereDistributionOrderDAO.findCommissionMoney(a.getDistributorId())))
                    //查询未结算佣金
                    .peek(a->a.setUnsettledMoney(cereDistributionOrderDAO.findUnsettledMoney(a.getDistributorId())))
                    .collect(Collectors.toList());
        }
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public AchievementDetai getOrderDetail(Long distributorId) throws CoBusinessException {
        AchievementDetai detai=new AchievementDetai();
        //根据佣金类型查询直接分销订单数
        detai.setDirectOrders(cereDistributionOrderDAO.getOrderDetailByType(distributorId, IntegerEnum.DIRECT_TYPE.getCode()));
        //根据佣金类型查询间接分销订单数
        detai.setIndirectOrders(cereDistributionOrderDAO.getOrderDetailByType(distributorId,IntegerEnum.IN_DIRECT_TYPE.getCode()));
        return detai;
    }

    @Override
    public AchievementDetai getDealMoneyDetail(Long distributorId) throws CoBusinessException {
        AchievementDetai detai=new AchievementDetai();
        //根据佣金类型查询直接分销金额
        detai.setDirectMoney(cereDistributionOrderDAO.getDealMoneyDetailByType(distributorId,IntegerEnum.DIRECT_TYPE.getCode()));
        //根据佣金类型查询间接分销金额
        detai.setIndirectMoney(cereDistributionOrderDAO.getDealMoneyDetailByType(distributorId,IntegerEnum.IN_DIRECT_TYPE.getCode()));
        return detai;
    }

    @Override
    public AchievementDetai getCommissionMoneyDetail(Long distributorId) throws CoBusinessException {
        AchievementDetai detai=new AchievementDetai();
        //根据佣金类型查询直接佣金
        detai.setDirectMoney(cereDistributionOrderDAO.getCommissionMoneyDetailByType(distributorId,IntegerEnum.DIRECT_TYPE.getCode()));
        //根据佣金类型查询间接佣金
        detai.setIndirectMoney(cereDistributionOrderDAO.getCommissionMoneyDetailByType(distributorId,IntegerEnum.IN_DIRECT_TYPE.getCode()));
        return detai;
    }

    @Override
    public AchievementDetai getNotCommissionMoneyDetail(Long distributorId) throws CoBusinessException {
        AchievementDetai detai=new AchievementDetai();
        //根据佣金类型查询直接佣金
        detai.setDirectMoney(cereDistributionOrderDAO.getNotCommissionMoneyDetailByType(distributorId,IntegerEnum.DIRECT_TYPE.getCode()));
        //根据佣金类型查询间接佣金
        detai.setIndirectMoney(cereDistributionOrderDAO.getNotCommissionMoneyDetailByType(distributorId,IntegerEnum.IN_DIRECT_TYPE.getCode()));
        return detai;
    }
}
