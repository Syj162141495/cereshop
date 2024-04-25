/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.activity;

import com.shop.cereshop.business.page.activity.ActivityProductData;
import com.shop.cereshop.business.page.activity.ActivitySign;
import com.shop.cereshop.business.page.finance.ShopBond;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.activity.ActivitiSignGetAllParam;
import com.shop.cereshop.business.param.activity.ActivityGetDataParam;
import com.shop.cereshop.business.param.activity.ActivitySignGetProductParam;
import com.shop.cereshop.business.param.finance.BondParam;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.CereShopGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereActivitySignDAO extends BaseMapper<CereActivitySign> {
    int deleteByPrimaryKey(Long signId);

    int insert(CereActivitySign record);

    int insertSelective(CereActivitySign record);

    CereActivitySign selectByPrimaryKey(Long signId);

    int updateByPrimaryKeySelective(CereActivitySign record);

    int updateByPrimaryKey(CereActivitySign record);

    List<ActivitySign> getAll(ActivitiSignGetAllParam param);

    List<Product> getById(@Param("signId") Long signId);

    List<Product> getProducts(ActivitySignGetProductParam param);

    List<CereShopGroup> getGroups(@Param("shopId") Long shopId);

    CereActivitySign findByShopIdAndActivityId(@Param("shopId") Long shopId,@Param("activityId") Long activityId);

    CerePlatformActivity findIfBond(@Param("activityId") Long activityId);

    CereActivitySign findBySignId(@Param("signId") Long signId);

    Integer findActivityState(@Param("activityId") Long activityId);

    BigDecimal findBondTotal(@Param("shopId") Long shopId);

    List<ShopBond> getAllBond(BondParam param);

    String findTransactionId(@Param("formid") String formid);

    Long findShopUserId(@Param("shopId") Long shopId);

    List<ActivitySign> getSeckillAll(ActivitiSignGetAllParam param);

    List<ActivitySign> getDiscountAll(ActivitiSignGetAllParam param);

    Integer findOrders(ActivityGetDataParam param);

    Integer findUsers(ActivityGetDataParam param);

    BigDecimal findTotal(ActivityGetDataParam param);

    List<ActivityProductData> findActivityProducts(ActivityGetDataParam param);

    Integer findValume(@Param("productId") Long productId,@Param("activityId") Long activityId,@Param("signType") Integer signType);

    BigDecimal findProductTotal(@Param("productId") Long productId,@Param("activityId") Long activityId,@Param("signType") Integer signType);

    CerePlatformSeckill findSeckillIfBond(@Param("activityId") Long activityId);

    CerePlatformDiscount findDiscountIfBond(@Param("activityId") Long activityId);
}
