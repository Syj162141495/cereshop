/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.label;

import com.shop.cereshop.commons.domain.label.CereShopLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereShopLabelDAO extends BaseMapper<CereShopLabel> {
    int deleteByPrimaryKey(Long labelId);

    int insert(CereShopLabel record);

    int insertSelective(CereShopLabel record);

    CereShopLabel selectByPrimaryKey(Long labelId);

    int updateByPrimaryKeySelective(CereShopLabel record);

    int updateByPrimaryKey(CereShopLabel record);
}
