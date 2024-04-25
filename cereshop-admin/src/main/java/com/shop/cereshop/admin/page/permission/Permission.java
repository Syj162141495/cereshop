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
 * 权限返回数据实体类
 */
@Data
@ApiModel(value = "Permission", description = "权限返回数据实体类")
public class Permission {

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    private Long permissionId;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private Long permissionPid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String permissionName;

    /**
     * 路由URI
     */
    @ApiModelProperty(value = "路由URI")
    private String permissionUri;

    /**
     * 组件
     */
    @ApiModelProperty(value = "组件")
    private String permission;

    /**
     * 图标地址
     */
    @ApiModelProperty(value = "图标地址")
    private String icon;

    /**
     * 文字描述
     */
    @ApiModelProperty(value = "文字描述")
    private String describe;

    /**
     * 权限类型
     */
    @ApiModelProperty(value = "权限类型")
    private String resourceType;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sort;

    /**
     * 子菜单数据
     */
    @ApiModelProperty(value = "子菜单数据")
    private List<Permission> childs;

    /**
     * 按钮权限数据
     */
    @ApiModelProperty(value = "按钮权限数据")
    private List<Permission> buttonPermissions;
}
