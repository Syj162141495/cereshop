/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 保证金返回数据实体类
 */
@Data
@ApiModel(value = "ActivityBond", description = "保证金返回数据实体类")
public class ActivityBond {

    /**
     * 保证金类型
     */
    @ApiModelProperty(value = "保证金类型")
    private String bondType;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private String signCode;

    /**
     * 保证金金额
     */
    @ApiModelProperty(value = "保证金金额")
    private BigDecimal bondMoney;

    /**
     * 保证金状态
     */
    @ApiModelProperty(value = "保证金状态")
    private Integer bondState;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String time;
}
