/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取标签详情
 */
@Data
@ApiModel(value = "LabelGetByIdParam", description = "获取标签详情")
public class LabelGetByIdParam {

    /**
     * 客户标签id
     */
    @ApiModelProperty(value = "客户标签id")
    private Long buyerLabelId;
}
