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
@ApiModel(value = "ExamineParam", description = "营销活动审核接收参数实体类")
public class ActivityExamineParam {

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败")
    private Integer state;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
