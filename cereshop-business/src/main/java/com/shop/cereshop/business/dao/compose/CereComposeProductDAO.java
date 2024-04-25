/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.compose;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereComposeProductDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CereComposeProduct record);

    int insertSelective(CereComposeProduct record);

    void insertBatch(@Param("list") List<CereComposeProduct> list);

    void deleteByComposeId(@Param("composeId") Long composeId);

    List<ComposeProduct> findProducts(@Param("composeId") Long composeId);
}
