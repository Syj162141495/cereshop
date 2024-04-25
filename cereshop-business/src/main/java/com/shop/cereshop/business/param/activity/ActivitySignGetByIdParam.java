/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取报名详情请求
 */
@Data
@ApiModel(value = "ActivitySignGetByIdParam", description = "获取报名详情请求")
public class ActivitySignGetByIdParam {

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;
}
