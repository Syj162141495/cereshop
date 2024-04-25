/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopCouponDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopCouponDetailDAO extends BaseMapper<CereShopCouponDetail> {
    int insert(CereShopCouponDetail record);

    int insertSelective(CereShopCouponDetail record);

    void insertBatch(@Param("list") List<CereShopCouponDetail> list);

    void deleteById(@Param("shopCouponId") Long shopCouponId);

    List<ToolProduct> findProducts(@Param("shopCouponId") Long shopCouponId);
}
