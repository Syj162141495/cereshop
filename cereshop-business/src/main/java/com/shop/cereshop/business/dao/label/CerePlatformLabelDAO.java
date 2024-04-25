/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.label;

import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CerePlatformLabelDAO extends BaseMapper<CerePlatformLabel> {
    int deleteByPrimaryKey(Long buyerLabelId);

    int insert(CerePlatformLabel record);

    int insertSelective(CerePlatformLabel record);

    CerePlatformLabel selectByPrimaryKey(Long buyerLabelId);

    int updateByPrimaryKeySelective(CerePlatformLabel record);

    int updateByPrimaryKey(CerePlatformLabel record);
}
