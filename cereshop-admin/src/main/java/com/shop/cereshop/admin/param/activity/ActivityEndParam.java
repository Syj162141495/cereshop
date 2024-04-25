/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "EndParam", description = "结束营销活动接收参数实体类")
public class ActivityEndParam {

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;
}
