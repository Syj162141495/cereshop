/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.after;

import com.shop.cereshop.commons.domain.after.CereAfterProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereAfterProductDAO extends BaseMapper<CereAfterProduct> {
    int insert(CereAfterProduct record);

    int insertSelective(CereAfterProduct record);
}
