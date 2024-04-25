/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加权限请求
 */
@Data
@ApiModel(value = "PermissionSaveParam", description = "添加权限请求")
public class PermissionSaveParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private Long permissionPid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
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
    @NotBlank(message = "权限类型不能为空")
    private String resourceType;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sort;

    /**
     * 权限所属项目
     */
    @ApiModelProperty(value = "权限所属项目")
    private Integer project;
}
