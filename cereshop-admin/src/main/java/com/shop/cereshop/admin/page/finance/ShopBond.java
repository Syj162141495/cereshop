/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商家保证金数据
 */
@Data
@ApiModel(value = "ShopBond", description = "商家保证金数据")
public class ShopBond {

    /**
     * 报名id
     */
    private Long signId;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 保证金类型
     */
    @ApiModelProperty(value = "保证金类型")
    private String bondType="营销活动保证金";

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private String transactionId;

    /**
     * 保证金金额
     */
    @ApiModelProperty(value = "保证金金额")
    private BigDecimal bondMoney;

    /**
     * 保证金状态 空-全部 0-待支付 1-冻结中 2-已退回
     */
    @ApiModelProperty(value = "保证金状态 空-全部 0-待支付 1-冻结中 2-已退回")
    private Integer bondState;

    /**
     * 缴纳时间
     */
    private String paymentTime;

    /**
     * 退保时间
     */
    private String returnTime;

    /**
     * 缴纳/退保时间
     */
    @ApiModelProperty(value = "缴纳/退保时间")
    private String time;
}
