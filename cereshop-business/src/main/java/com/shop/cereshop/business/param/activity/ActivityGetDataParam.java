/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取活动数据请求
 */
@Data
@ApiModel(value = "ActivityGetDataParam", description = "获取活动数据请求")
public class ActivityGetDataParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

    /**
     * 活动类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣
     */
    @ApiModelProperty(value = "活动类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣")
    private Integer signType;
}
