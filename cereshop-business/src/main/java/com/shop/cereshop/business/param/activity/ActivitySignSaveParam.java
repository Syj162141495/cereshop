/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报名请求
 */
@Data
@ApiModel(value = "ActivitySignSaveParam", description = "报名请求")
public class ActivitySignSaveParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 报名类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣
     */
    @ApiModelProperty(value = "报名类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣")
    private Integer signType;

    /**
     * 关联活动id
     */
    @ApiModelProperty(value = "关联活动id")
    private Long activityId;

    /**
     * 保证金
     */
    @ApiModelProperty(value = "保证金")
    private BigDecimal bondMoney;

    /**
     * 商品明细数据
     */
    @ApiModelProperty(value = "商品明细数据")
    private List<SignProductParam> products;
}
