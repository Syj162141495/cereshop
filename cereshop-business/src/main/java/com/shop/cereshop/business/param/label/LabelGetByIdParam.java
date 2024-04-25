/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取标签详情请求
 */
@Data
@ApiModel(value = "LabelGetByIdParam", description = "获取标签详情请求")
public class LabelGetByIdParam {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;

    /**
     * 标签id数组
     */
    @ApiModelProperty(value = "标签id数组")
    private List<Long> ids;
}
