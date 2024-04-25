/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.distributor;

import com.shop.cereshop.commons.domain.distributor.CereDistributionOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereDistributionOrderDAO extends BaseMapper<CereDistributionOrder> {
    int deleteByPrimaryKey(Long orderId);

    int insert(CereDistributionOrder record);

    int insertSelective(CereDistributionOrder record);

    CereDistributionOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(CereDistributionOrder record);

    int updateByPrimaryKey(CereDistributionOrder record);
}
