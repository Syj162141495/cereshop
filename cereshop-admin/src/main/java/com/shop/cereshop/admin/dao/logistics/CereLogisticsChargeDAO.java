/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.logistics;

import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereLogisticsChargeDAO extends BaseMapper<CereLogisticsCharge> {
    int insert(CereLogisticsCharge record);

    int insertSelective(CereLogisticsCharge record);
}
