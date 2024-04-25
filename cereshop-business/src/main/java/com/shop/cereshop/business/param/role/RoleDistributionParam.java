/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单分配请求
 */
@Data
@ApiModel(value = "RoleDistributionParam", description = "菜单分配请求")
public class RoleDistributionParam {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 权限id数组
     */
    @ApiModelProperty(value = "权限id数组")
    private List<Long> permissionIds;
}
