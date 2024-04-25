/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereShopComposeDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long composeId);

    int insert(CereShopCompose record);

    int insertSelective(CereShopCompose record);

    CereShopCompose selectByPrimaryKey(Long composeId);

    int updateByPrimaryKeySelective(CereShopCompose record);

    int updateByPrimaryKey(CereShopCompose record);
}
