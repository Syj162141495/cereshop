/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 营销活动列表返回数据实体类
 */
@Data
@ApiModel(value = "Activity", description = "营销活动列表返回数据实体类")
public class Activity {

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
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束
     */
    @ApiModelProperty(value = "活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束")
    private Integer state;

    /**
     * 优惠方式 1-满减 2-优惠券
     */
    @ApiModelProperty(value = "优惠方式 1-满减 2-优惠券")
    private Integer discountMode;

    /**
     * 商家数
     */
    @ApiModelProperty(value = "商家数")
    private Integer shopNumber;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer productNumber;
}
