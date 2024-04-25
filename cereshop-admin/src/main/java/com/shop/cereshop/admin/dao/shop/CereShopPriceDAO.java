/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CereShopPrice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereShopPriceDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long priceId);

    int insert(CereShopPrice record);

    int insertSelective(CereShopPrice record);

    CereShopPrice selectByPrimaryKey(Long priceId);

    int updateByPrimaryKeySelective(CereShopPrice record);

    int updateByPrimaryKey(CereShopPrice record);
}
