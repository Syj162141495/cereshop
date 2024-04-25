/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.user;

import com.shop.cereshop.admin.page.role.PlatformUserRole;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.user.CerePlatformUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformUserRoleDAO extends BaseMapper<CerePlatformUserRole> {
    int insert(CerePlatformUserRole record);

    int insertSelective(CerePlatformUserRole record);

    void deleteByUserId(@Param("platformUserId") Long platformUserId);

    List<PlatformUserRole> findRolesByUserId(@Param("platformUserId") Long platformUserId);

}
