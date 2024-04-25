/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.platformtool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_platform_polite_activity 平台支付有礼优惠券信息实体
 * @author 
 */
@Data
public class CerePlatformPoliteActivity implements Serializable {
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

    private static final long serialVersionUID = 1L;
}
