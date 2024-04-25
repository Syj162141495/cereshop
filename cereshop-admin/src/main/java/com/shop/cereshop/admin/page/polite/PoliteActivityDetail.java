/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.polite;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平台支付有礼优惠券信息数据
 * @author 
 */
@Data
public class PoliteActivityDetail implements Serializable {
    /**
     * 平台支付有礼活动id
     */
    private Long politeId;

    /**
     * 平台优惠券活动id
     */
    @ApiModelProperty(value = "平台优惠券活动id")
    private Long activityId;

    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String activityName;

    /**
     * 优惠券类型 1-满减 2-折扣
     */
    @ApiModelProperty(value = "优惠券类型 1-满减 2-折扣")
    private Integer activityType;

    /**
     * 使用门槛满多少元，无门槛为0
     */
    @ApiModelProperty(value = "使用门槛满多少元，无门槛为0")
    private BigDecimal threshold;

    /**
     * 优惠内容减多少元/打多少折
     */
    @ApiModelProperty(value = "优惠内容减多少元/打多少折")
    private BigDecimal couponContent;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private String endTime;

    private static final long serialVersionUID = 1L;
}
