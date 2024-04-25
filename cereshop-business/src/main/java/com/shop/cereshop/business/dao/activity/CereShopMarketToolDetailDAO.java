/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.activity;

import com.shop.cereshop.commons.domain.activity.CereShopMarketToolDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopMarketToolDetailDAO extends BaseMapper<CereShopMarketToolDetail> {
    int insert(CereShopMarketToolDetail record);

    int insertSelective(CereShopMarketToolDetail record);

    void insertBatch(@Param("list") List<CereShopMarketToolDetail> list);

    void deleteByToolId(@Param("toolId") Long toolId);

    List<CereShopMarketToolDetail> findByToolId(@Param("toolId") Long toolId);
}
