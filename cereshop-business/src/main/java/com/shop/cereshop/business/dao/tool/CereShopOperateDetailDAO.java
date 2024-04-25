/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.commons.domain.tool.CereShopOperateDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopOperateDetailDAO extends BaseMapper<CereShopOperateDetail> {
    int insert(CereShopOperateDetail record);

    int insertSelective(CereShopOperateDetail record);

    void insertBatch(@Param("list") List<CereShopOperateDetail> list);

    List<OperateCoupon> findCoupons(@Param("shopOperateId") Long shopOperateId);
}
