/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.activity;

import com.shop.cereshop.business.page.canvas.CanvasCoupon;
import com.shop.cereshop.business.page.canvas.CanvasCouponDetail;
import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<CanvasCouponDetail> findDetail(@Param("activityId") Long activityId);

    List<CerePlatformActivity> findPlatformCoupon();

    void updateActivityEndState(@Param("list") List<CerePlatformActivity> list,@Param("time") String time);
}
