/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单数据
 */
@Data
@ApiModel(value = "RolePermission", description = "角色菜单数据")
public class RolePermission {

    /**
     * 菜单层级数据
     */
    @ApiModelProperty(value = "菜单层级数据")
    private List<Permission> permissions;

    /**
     * 已选中的菜单id数组
     */
    @ApiModelProperty(value = "已选中的菜单id数组")
    private List<Long> ids;
}
