/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.activity;

import com.shop.cereshop.business.page.activity.MarketTool;
import com.shop.cereshop.business.param.tool.ToolGetAllParam;
import com.shop.cereshop.commons.domain.activity.CereShopMarketTool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopMarketToolDAO extends BaseMapper<CereShopMarketTool> {
    int deleteByPrimaryKey(Long toolId);

    int insert(CereShopMarketTool record);

    int insertSelective(CereShopMarketTool record);

    CereShopMarketTool selectByPrimaryKey(Long toolId);

    int updateByPrimaryKeySelective(CereShopMarketTool record);

    int updateByPrimaryKey(CereShopMarketTool record);

    MarketTool getById(@Param("toolId") Long toolId);

    List<MarketTool> getAll(ToolGetAllParam param);
}
