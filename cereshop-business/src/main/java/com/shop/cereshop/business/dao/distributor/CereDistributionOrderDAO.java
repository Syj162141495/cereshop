/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.distributor;

import com.shop.cereshop.business.page.distribution.Achievement;
import com.shop.cereshop.business.page.distribution.DistributionOrder;
import com.shop.cereshop.business.param.distributor.DistributorGetAllAchievementParam;
import com.shop.cereshop.business.param.distributorOrder.OrderGetAllParam;
import com.shop.cereshop.commons.domain.distributor.CereDistributionOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereDistributionOrderDAO extends BaseMapper<CereDistributionOrder> {
    int deleteByPrimaryKey(Long orderId);

    int insert(CereDistributionOrder record);

    int insertSelective(CereDistributionOrder record);

    CereDistributionOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(CereDistributionOrder record);

    int updateByPrimaryKey(CereDistributionOrder record);

    List<DistributionOrder> getAll(OrderGetAllParam param);

    List<CereDistributionOrder> findChilds(OrderGetAllParam param);

    void updateStatByIds(@Param("ids") List<Long> ids);

    List<Achievement> getAllAchievement(DistributorGetAllAchievementParam param);

    Integer findOrders(@Param("distributorId") Long distributorId);

    BigDecimal findDealMoney(@Param("distributorId") Long distributorId);

    BigDecimal findCommissionMoney(@Param("distributorId") Long distributorId);

    BigDecimal findUnsettledMoney(@Param("distributorId") Long distributorId);

    Integer getOrderDetailByType(@Param("distributorId") Long distributorId,@Param("type") Integer type);

    BigDecimal getDealMoneyDetailByType(@Param("distributorId") Long distributorId,@Param("type") Integer type);

    BigDecimal getCommissionMoneyDetailByType(@Param("distributorId") Long distributorId,@Param("type") Integer type);

    BigDecimal getNotCommissionMoneyDetailByType(@Param("distributorId") Long distributorId,@Param("type") Integer type);
}
