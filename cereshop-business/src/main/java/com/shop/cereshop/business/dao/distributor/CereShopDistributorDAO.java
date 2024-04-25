/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.distributor;

import com.shop.cereshop.business.page.distribution.ShopDistributor;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.param.shopDistributor.ShopDistributorGetAllParam;
import com.shop.cereshop.business.param.shopDistributor.ShopDistributorGetStayParam;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopDistributorDAO extends BaseMapper<CereShopDistributor> {
    int deleteByPrimaryKey(Long distributorId);

    int insert(CereShopDistributor record);

    int insertSelective(CereShopDistributor record);

    CereShopDistributor selectByPrimaryKey(Long distributorId);

    int updateByPrimaryKeySelective(CereShopDistributor record);

    int updateByPrimaryKey(CereShopDistributor record);

    List<ShopDistributor> getAll(ShopDistributorGetAllParam param);

    CereShopDistributor findSubordinate(@Param("distributorId") Long distributorId);

    Integer findTotal(@Param("distributorId") Long distributorId);

    Integer findSubordinateTotal(@Param("distributorId") Long distributorId);

    BigDecimal findMoney(@Param("distributorId") Long distributorId);

    List<CereShopDistributor> getAllInvitees(ShopParam param);

    List<ShopDistributor> getStayExamineAll(ShopDistributorGetStayParam param);

    Integer findOrderTotal(@Param("distributorId") Long distributorId);

    BigDecimal findOrderMoney(@Param("distributorId") Long distributorId);

    ShopDistributor getById(@Param("distributorId") Long distributorId);

    CereShopDistributor findByShopIdAndUserId(@Param("shopId") Long shopId,@Param("buyerUserId") Long buyerUserId);

    CereShopDistributor checkPhone(@Param("distributorPhone") String distributorPhone,
                                   @Param("distributorId") Long distributorId,@Param("shopId") Long shopId);

    CereBuyerUser checkBuyerUser(@Param("distributorPhone") String distributorPhone);

    void updateReliveBindByShopId(@Param("shopId") Long shopId,@Param("time") String time);

    void updateBatch(@Param("list") List<CereShopDistributor> list);
}
