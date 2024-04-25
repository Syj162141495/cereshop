/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CerePriceRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePriceRuleDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CerePriceRule record);

    int insertSelective(CerePriceRule record);

    void insertBatch(@Param("list") List<CerePriceRule> list);

    void deleteByPriceId(@Param("priceId") Long priceId);

    List<CerePriceRule> findRules(@Param("priceId") Long priceId);
}
