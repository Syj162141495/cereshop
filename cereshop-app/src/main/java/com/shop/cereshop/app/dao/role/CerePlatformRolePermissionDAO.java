/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.dao.role;

import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CerePlatformRolePermissionDAO extends BaseMapper<CerePlatformRolePermission> {
    int insert(CerePlatformRolePermission record);

    int insertSelective(CerePlatformRolePermission record);

}
