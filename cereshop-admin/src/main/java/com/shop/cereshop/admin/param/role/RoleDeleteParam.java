/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除角色请求
 */
@Data
@ApiModel(value = "RoleDeleteParam", description = "删除角色请求")
public class RoleDeleteParam {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
