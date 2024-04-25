/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.platformtool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformDiscountDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long discountId);

    int insert(CerePlatformDiscount record);

    int insertSelective(CerePlatformDiscount record);

    CerePlatformDiscount selectByPrimaryKey(Long discountId);

    int updateByPrimaryKeySelective(CerePlatformDiscount record);

    int updateByPrimaryKey(CerePlatformDiscount record);

    CanvasPlatformDiscount getMinDiscount();

    List<Long> checkPlatformDiscount(@Param("ids") List<Long> ids, @Param("startTime") String startTime,
                                     @Param("endTime") String endTime, @Param("shopId") Long shopId);

    List<CerePlatformDiscount> findPlatformDiscount();

    void updatePlatformDiscountEndState(@Param("list") List<CerePlatformDiscount> list,@Param("time") String time);
}
