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
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "GetAllParam", description = "营销活动管理查询接收参数实体类")
public class ActivityGetAllParam extends PageParam {

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败")
    private Integer state;

    /**
     * 优惠方式 1-满减 2-优惠券
     */
    @ApiModelProperty(value = "优惠方式 1-满减 2-优惠券")
    private Integer discountMode;
}
