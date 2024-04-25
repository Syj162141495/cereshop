/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.permission;

import com.shop.cereshop.business.page.permission.MenuButton;
import com.shop.cereshop.business.page.permission.Permission;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformPermissionDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long permissionId);

    int insert(CerePlatformPermission record);

    int insertSelective(CerePlatformPermission record);

    CerePlatformPermission selectByPrimaryKey(Long permissionId);

    int updateByPrimaryKeySelective(CerePlatformPermission record);

    int updateByPrimaryKey(CerePlatformPermission record);

    CerePlatformPermission getById(@Param("permissionId") Long permissionId);

    List<Permission> findAllByResourceType(@Param("resourceType") String resourceType,
                                           @Param("permissionName") String permissionName,@Param("shopId") Long shopId);

    List<MenuButton> findAllCatalogByUserIdAndResourceType(@Param("businessUserId") Long businessUserId,
                                                           @Param("resourceType") String resourceType,@Param("shopId") Long shopId);

    CerePlatformPermission findBySort(@Param("sort") Integer sort,@Param("id") Long id,@Param("shopId") Long shopId);

    Integer getMaxSort();

    List<Long> findRolePermissionIds(@Param("roleId") Long roleId);

    List<Permission> findChilds(@Param("permissionName") String permissionName,@Param("shopId") Long shopId);

    int findMaxSort();
}
