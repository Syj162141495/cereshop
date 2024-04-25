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
 * 获取营销活动列表请求
 */
@Data
@ApiModel(value = "ActivitiSignGetAllParam", description = "获取营销活动列表请求")
public class ActivitiSignGetAllParam extends PageParam {

    @ApiModelProperty(value = "查询类型 1-全部平台活动 2-我参与的平台活动")
    private Integer type;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 活动类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣
     */
    @ApiModelProperty(value = "活动类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣")
    private Integer signType;

    /**
     * 活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束
     */
    @ApiModelProperty(value = "活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束")
    private Integer state;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败")
    private Integer examineState;
}
