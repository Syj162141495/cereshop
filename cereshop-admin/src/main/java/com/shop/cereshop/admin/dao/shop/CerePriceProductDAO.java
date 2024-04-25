/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CerePriceProductDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CerePriceProduct record);

    int insertSelective(CerePriceProduct record);
}
