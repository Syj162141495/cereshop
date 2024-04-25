/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.order;

import com.shop.cereshop.commons.domain.order.CereOrderReturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereOrderReturnDAO extends BaseMapper<CereOrderReturn> {
    int deleteByPrimaryKey(Long returnId);

    int insert(CereOrderReturn record);

    int insertSelective(CereOrderReturn record);

    CereOrderReturn selectByPrimaryKey(Long returnId);

    int updateByPrimaryKeySelective(CereOrderReturn record);

    int updateByPrimaryKey(CereOrderReturn record);
}
