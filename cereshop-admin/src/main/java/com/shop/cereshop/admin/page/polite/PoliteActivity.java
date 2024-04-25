/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.polite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平台优惠券选择数据
 */
@Data
@ApiModel(value = "PoliteActivity", description = "平台优惠券选择数据")
public class PoliteActivity implements Serializable {

    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
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
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 到期日
     */
    @ApiModelProperty(value = "到期日")
    private String time;

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
