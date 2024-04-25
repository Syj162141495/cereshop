/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.business.page.shop.ShopRelationship;
import com.shop.cereshop.business.param.ship.ShipGetAllParam;
import com.shop.cereshop.business.param.shop.CereShopRelationshipParam;
import com.shop.cereshop.commons.domain.shop.CereShopRelationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopRelationshipDAO extends BaseMapper<CereShopRelationship> {
    int insert(CereShopRelationship record);

    int insertSelective(CereShopRelationship record);

    void update(CereShopRelationship relationship);

    List<ShopRelationship> getAll(ShipGetAllParam param);

    CereShopRelationship getById(@Param("shopId") Long shopId);

}
