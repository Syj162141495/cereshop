/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.activity;

import com.shop.cereshop.commons.constant.IntegerEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 营销活动数据返回实体类
 */
@Data
@ApiModel(value = "ActivitySign", description = "营销活动数据返回实体类")
public class ActivitySign {

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

    /**
     * 报名状态 0-待审核 1-报名成功 2-报名失败
     */
    @ApiModelProperty(value = "报名状态 0-待审核 1-报名成功 2-报名失败")
    private Integer signState;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 活动介绍
     */
    @ApiModelProperty(value = "活动介绍")
    private String activityIntroduce;

    /**
     * 保证金
     */
    @ApiModelProperty(value = "保证金")
    private BigDecimal bondMoney;

    /**
     * 报名时间
     */
    @ApiModelProperty(value = "报名时间")
    private String signTime;

    /**
     * 活动时间
     */
    @ApiModelProperty(value = "活动时间")
    private String activityTime;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private String activityStartTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private String activityEndTime;

    /**
     * 优惠方式 1-满减 2-优惠券
     */
    @ApiModelProperty(value = "优惠方式 1-满减 2-优惠券")
    private Integer discountMode;

    /**
     * 优惠方案 1-叠加优惠 2-阶梯优惠
     */
    @ApiModelProperty(value = "优惠方案 1-叠加优惠 2-阶梯优惠")
    private Integer discountProgramme;

    /**
     * 活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束
     */
    @ApiModelProperty(value = "活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束")
    private Integer state;

    /**
     * 操作按钮标识 1-立即报名（高亮） 2-立即报名（置灰） 3-报名详情 4-重新报名
     */
    @ApiModelProperty(value = "操作按钮标识 1-立即报名（高亮） 2-立即报名（置灰） 3-报名详情 4-重新报名")
    private Integer operation;

    /**
     * 方案明细数据
     */
    @ApiModelProperty(value = "方案明细数据")
    private String details;

    /**
     * 距报名剩余时间(s)
     */
    @ApiModelProperty(value = "距报名剩余时间(s)")
    private long time;

    /**
     * 主图
     */
    @ApiModelProperty(value = "主图")
    private String productImage;

    /**
     * 是否需要保证金 1-是 0-否
     */
    private Integer ifBond;
}
