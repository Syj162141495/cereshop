/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取活动数据请求
 */
@Data
@ApiModel(value = "ActivityGetDatasParam", description = "获取活动数据请求")
public class ActivityGetDatasParam extends PageParam {

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;
}
