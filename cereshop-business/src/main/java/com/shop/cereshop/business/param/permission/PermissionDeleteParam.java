/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除权限请求
 */
@Data
@ApiModel(value = "PermissionDeleteParam", description = "删除权限请求")
public class PermissionDeleteParam {

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    private Long permissionId;
}
