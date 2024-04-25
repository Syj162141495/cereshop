/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import com.shop.cereshop.commons.domain.activity.CerePlatformActivityDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "CerePlatformActivityParam", description = "添加营销活动接收参数实体类")
public class ActivitySaveParam {

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    @NotBlank(message = "活动名称不能为空")
    private String activityName;

    /**
     * 活动备注
     */
    @ApiModelProperty(value = "活动备注")
    private String activityIntroduce;

    /**
     * 报名开始时间
     */
    @ApiModelProperty(value = "报名开始时间")
    @NotBlank(message = "报名开始时间不能为空")
    private String signStartTime;

    /**
     * 报名结束时间
     */
    @ApiModelProperty(value = "报名结束时间")
    @NotBlank(message = "报名结束时间不能为空")
    private String signEndTime;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    @NotBlank(message = "活动开始时间不能为空")
    private String activityStartTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    @NotBlank(message = "活动结束时间不能为空")
    private String activityEndTime;

    /**
     * 是否需要保证金  1-是 0-否
     */
    @ApiModelProperty(value = "是否需要保证金  1-是 0-否")
    private Integer ifBond;

    /**
     * 保证金金额
     */
    @ApiModelProperty(value = "保证金金额")
    private BigDecimal bondMoney;

    /**
     * 使用门槛满多少元，无门槛为0
     */
    @ApiModelProperty(value = "使用门槛满多少元，无门槛为0")
    @NotNull(message = "使用门槛不能为空")
    private BigDecimal threshold;

    /**
     * 优惠方式 1-满减 2-折扣
     */
    @ApiModelProperty(value = "优惠方式 1-满减 2-折扣")
    private Integer discountMode;

    /**
     * 优惠内容减多少元/打多少折
     */
    @ApiModelProperty(value = "优惠内容减多少元/打多少折")
    @NotNull(message = "优惠内容不能为空")
    private BigDecimal couponContent;

    /**
     * 发放数量
     */
    @ApiModelProperty(value = "发放数量")
    @NotNull(message = "发放数量不能为空")
    private Integer number;

    /**
     * 每人限领次数  1-无限次 2-限制几次
     */
    @ApiModelProperty(value = "每人限领次数  1-无限次 2-限制几次")
    @NotNull(message = "每人限领次数不能为空")
    private Integer receiveType;

    /**
     * 限制次数
     */
    @ApiModelProperty(value = "限制次数")
    private Integer frequency;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String image;

    /**
     * 优惠方案明细数据
     */
    @ApiModelProperty(value = "优惠方案明细数据")
    @Valid
    private List<CerePlatformActivityDetail> promotionDetail;
}
