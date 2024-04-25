/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.logistics;

import com.shop.cereshop.business.page.logistics.Charge;
import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereLogisticsChargeDAO extends BaseMapper<CereLogisticsCharge> {
    int insert(CereLogisticsCharge record);

    int insertSelective(CereLogisticsCharge record);

    void insertBatch(@Param("list") List<CereLogisticsCharge> list);

    void deleteByLogisticsId(@Param("logisticsId") Long logisticsId);

    List<Charge> findByLogisticsId(@Param("logisticsId") Long logisticsId);
}
