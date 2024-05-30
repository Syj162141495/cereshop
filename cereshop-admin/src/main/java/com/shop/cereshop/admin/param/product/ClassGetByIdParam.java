/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取类别详情请求
 */
@Data
@ApiModel(value = "ClassifyGetByIdParam", description = "获取类别详情请求")
public class ClassGetByIdParam {

    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id")
    private Long classifyId;
}
