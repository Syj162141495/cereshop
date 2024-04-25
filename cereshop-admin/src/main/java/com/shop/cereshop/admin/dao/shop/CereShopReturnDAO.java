/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.shop.cereshop.commons.domain.shop.CereShopReturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereShopReturnDAO extends BaseMapper<CereShopReturn> {
    int insert(CereShopReturn record);

    int insertSelective(CereShopReturn record);
}
