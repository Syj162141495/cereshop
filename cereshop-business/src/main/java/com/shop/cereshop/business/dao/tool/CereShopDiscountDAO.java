/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.CouponProduct;
import com.shop.cereshop.business.page.tool.ShopDiscount;
import com.shop.cereshop.business.page.tool.ShopDiscountDetail;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.ShopDiscountGetAllParam;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopDiscountDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopDiscountDAO extends BaseMapper<CereShopDiscount> {
    int deleteByPrimaryKey(Long shopDiscountId);

    int insert(CereShopDiscount record);

    int insertSelective(CereShopDiscount record);

    CereShopDiscount selectByPrimaryKey(Long shopDiscountId);

    int updateByPrimaryKeySelective(CereShopDiscount record);

    int updateByPrimaryKey(CereShopDiscount record);

    ShopDiscountDetail getById(@Param("shopDiscountId") Long shopDiscountId);

    List<ShopDiscount> getAll(ShopDiscountGetAllParam param);

    List<ToolProduct> getProducts(@Param("shopId") Long shopId);

    List<CouponProduct> findDataProducts(@Param("shopDiscountId") Long shopDiscountId,@Param("shopId") Long shopId);

    String findDiscountName(@Param("shopDiscountId") Long shopDiscountId);

    Integer findPays(@Param("shopDiscountId") Long shopDiscountId,@Param("shopId") Long shopId);

    BigDecimal findTotal(@Param("shopDiscountId") Long shopDiscountId,@Param("shopId") Long shopId);

    List<CereShopDiscount> checkTime(CereShopDiscount cereShopDiscount);

    List<Long> checkOtherSeckill(@Param("ids") List<Long> ids, @Param("startTime") String startTime,
                                 @Param("endTime") String endTime, @Param("shopId") Long shopId);

    List<Long> checkOtherWork(@Param("ids") List<Long> ids,@Param("startTime") String startTime,
                                  @Param("endTime") String endTime,@Param("shopId") Long shopId);

    void updateState(CereShopDiscount cereShopDiscount);

    List<ShopDiscountDetail> getDiscounts(RenovationParam param);

    List<CereShopDiscount> checkUpdateTime(CereShopDiscount cereShopDiscount);

    int findVisit(Long shopDiscountId);

    List<CereShopDiscount> findShopDiscount();

    void updateDiscountEndState(@Param("list") List<CereShopDiscount> list,@Param("time") String time);
}
