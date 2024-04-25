/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.label;

import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereBuyerLabelDAO extends BaseMapper<CereBuyerLabel> {
    int insert(CereBuyerLabel record);

    int insertSelective(CereBuyerLabel record);
}
