/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限属性
 */
@Data
@ApiModel(value = "Meta", description = "权限属性")
public class Meta {

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "名称")
    private String title;

    /**
     * 图标地址
     */
    @ApiModelProperty(value = "路由URI")
    private String icon;
}
