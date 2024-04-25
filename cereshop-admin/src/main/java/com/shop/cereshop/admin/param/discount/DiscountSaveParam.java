/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.discount;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增限时折扣秒杀活动请求
 */
@Data
@ApiModel(value = "DiscountSaveParam", description = "新增限时折扣秒杀活动请求")
public class DiscountSaveParam implements Serializable {

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称",required = true)
    @NotBlank(message = "活动名称不能为空")
    private String discountName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    @NotBlank(message = "活动结束时间不能为空")
    private String endTime;

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
     * 折扣
     */
    @ApiModelProperty(value = "折扣")
    @NotNull(message = "折扣不能为空")
    private BigDecimal discount;

    /**
     * 商品限购 1-不限购 2-限购
     */
    @ApiModelProperty(value = "商品限购 1-不限购 2-限购",required = true)
    @NotNull(message = "商品限购不能为空")
    private Integer ifLimit;

    /**
     * 限购几件/人
     */
    @ApiModelProperty(value = "限购几件/人")
    private Integer limitNumber;

    /**
     * 优惠券是否叠加 1-是 0-否
     */
    @ApiModelProperty(value = "优惠券是否叠加 1-是 0-否",required = true)
    @NotNull(message = "优惠券是否叠加不能为空")
    private Integer ifAdd;

    private static final long serialVersionUID = 1L;

}
