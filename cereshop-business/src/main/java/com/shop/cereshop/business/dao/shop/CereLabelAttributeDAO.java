/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.commons.domain.shop.CereLabelAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereLabelAttributeDAO extends BaseMapper<CereLabelAttribute> {
    int insert(CereLabelAttribute record);

    int insertSelective(CereLabelAttribute record);
}
