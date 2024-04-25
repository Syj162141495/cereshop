/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.shopcheck.CheckGetAllParam;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.shop.CereShopCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopCheckDAO extends BaseMapper<CereShopCheck> {
    int insert(CereShopCheck record);

    int insertSelective(CereShopCheck record);

    List<Shop> getAll(CheckGetAllParam param);

    void deleteById(@Param("checkId") Long checkId);

    List<Shop> getStayAll(CheckGetAllParam param);
}
