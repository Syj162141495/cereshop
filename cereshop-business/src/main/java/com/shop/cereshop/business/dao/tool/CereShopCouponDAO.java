/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.canvas.ProductCoupon;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.tool.*;
import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.business.param.tool.OperateCouponParam;
import com.shop.cereshop.business.param.tool.ShopCouponGetAllParam;
import com.shop.cereshop.commons.domain.tool.CereShopCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopCouponDAO extends BaseMapper<CereShopCoupon> {
    int deleteByPrimaryKey(Long shopCouponId);

    int insert(CereShopCoupon record);

    int insertSelective(CereShopCoupon record);

    CereShopCoupon selectByPrimaryKey(Long shopCouponId);

    int updateByPrimaryKeySelective(CereShopCoupon record);

    int updateByPrimaryKey(CereShopCoupon record);

    List<Long> findProductIdsByShopId(@Param("shopId") Long shopId);

    List<Long> findProductIdsByShopIdAndIds(@Param("shopId") Long shopId,@Param("ids") List<Long> ids);

    ShopCouponDetail getById(@Param("shopCouponId") Long shopCouponId);

    List<ShopCoupon> getAll(ShopCouponGetAllParam param);

    List<ToolProduct> getProducts(@Param("shopId") Long shopId);

    List<OperateCoupon> selectCoupon(OperateCouponParam param);

    List<CouponProduct> findDataProducts(@Param("shopCouponId") Long shopCouponId,@Param("shopId") Long shopId,@Param("time") String time);

    String findCouponName(@Param("shopCouponId") Long shopCouponId);

    BigDecimal findTotal(@Param("shopCouponId") Long shopCouponId,@Param("shopId") Long shopId,@Param("time") String time);

    BigDecimal findUseMoney(@Param("shopCouponId") Long shopCouponId,@Param("time") String time);

    Integer findCount(@Param("shopCouponId") Long shopCouponId,@Param("shopId") Long shopId,@Param("time") String time);

    void updateState(CereShopCoupon cereShopCoupon);

    void updateBuyerCouponState(@Param("shopCouponId") Long shopCouponId);

    List<CereShopCoupon> findAllByShopId(@Param("shopId") Long shopId);

    List<ProductCoupon> getShopCoupons(CanvasCouponParam param);
}
