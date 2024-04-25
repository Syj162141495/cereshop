/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.dao.activity;

import com.shop.cereshop.app.page.canvas.CanvasCoupon;
import com.shop.cereshop.app.page.canvas.CanvasCouponDetail;
import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.app.page.tool.ToolProduct;
import com.shop.cereshop.app.param.canvas.CanvasCouponParam;
import com.shop.cereshop.app.param.canvas.RenovationParam;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformActivityDAO extends BaseMapper<CerePlatformActivity> {
    int deleteByPrimaryKey(Long activityId);

    int insert(CerePlatformActivity record);

    int insertSelective(CerePlatformActivity record);

    CerePlatformActivity selectByPrimaryKey(Long activityId);

    int updateByPrimaryKeySelective(CerePlatformActivity record);

    int updateByPrimaryKey(CerePlatformActivity record);

    List<CanvasCoupon> getCoupons(CanvasCouponParam param);

    List<CanvasCouponDetail> findDetai(@Param("activityId") Long activityId);

    List<ProductCoupon> findCouponByProductId(@Param("productId") Long productId);

    List<ProductCoupon> findCouponByProductIdAndUserId(@Param("buyerUserId") Long buyerUserId,@Param("productId") Long productId);

    List<CanvasCoupon> getUserCoupons(CanvasCouponParam param);

    List<CerePlatformActivity> findOnGoingCouponByBatchId(List<Long> couponIdList);

    List<ToolProduct> getGroupWorkProducts(RenovationParam param);
}
