/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.logistics;

import com.shop.cereshop.business.page.logistics.Logistics;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.commons.domain.logistics.CereOrderLogistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereOrderLogisticsDAO extends BaseMapper<CereOrderLogistics> {
    int deleteByPrimaryKey(Long logisticsId);

    int insert(CereOrderLogistics record);

    int insertSelective(CereOrderLogistics record);

    CereOrderLogistics selectByPrimaryKey(Long logisticsId);

    int updateByPrimaryKeySelective(CereOrderLogistics record);

    int updateByPrimaryKey(CereOrderLogistics record);

    Logistics getById(@Param("logisticsId") Long logisticsId);

    List<Logistics> getAll(ShopParam param);
}
