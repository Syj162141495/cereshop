/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.permission;

import com.shop.cereshop.admin.page.permission.MenuButton;
import com.shop.cereshop.admin.page.permission.Permission;
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

    List<Permission> findAllByResourceType(@Param("resourceType") String resourceType,@Param("permissionName") String permissionName);

    List<MenuButton> findAllCatalogByUserIdAndResourceType(@Param("platformUserId") Long platformUserId, @Param("resourceType") String resourceType);

    CerePlatformPermission findBySort(@Param("sort") Integer sort,@Param("id") Long id);

    Integer getMaxSort();

    List<Long> findRolePermissionIds(@Param("roleId") Long roleId);

    List<Permission> findChilds(@Param("permissionName") String permissionName);

    List<MenuButton> findAllCatalogByResourceType(@Param("resourceType") String resourceType);

    int findMaxSort();

    List<CerePlatformPermission> findByPermissionAndProject(@Param("project") Long project,
                                                            @Param("permission") String permission,
                                                            @Param("resourceType") String resourceType);

    Long getMaxId();

    void insertBatch(@Param("list") List<CerePlatformPermission> list);
}
