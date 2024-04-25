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
 * 权限数据
 */
@Data
@ApiModel(value = "MenuButton", description = "权限数据")
public class MenuButton {

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
     * 权限类型
     */
    @ApiModelProperty(value = "权限类型")
    private String resourceType;

    /**
     * path路径
     */
    @ApiModelProperty(value = "path路径")
    private String path;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String permissionName;

    /**
     * 图标地址
     */
    @ApiModelProperty(value = "图标地址")
    private String icon;

    /**
     * 属性
     */
    @ApiModelProperty(value = "属性")
    private Meta meta;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<MenuButton> children;

    /**
     * 按钮数据
     */
    @ApiModelProperty(value = "按钮数据")
    private List<MenuButton> buttons;
}
