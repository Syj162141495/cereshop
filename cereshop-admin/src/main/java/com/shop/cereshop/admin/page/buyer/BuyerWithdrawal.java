/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户提现数据
 */
@Data
@ApiModel(value = "BuyerUserDetail", description = "用户提现数据")
public class BuyerWithdrawal {

    /**
     * 提现id
     */
    @ApiModelProperty(value = "提现id")
    private Long withdrawalId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalMoney;

    /**
     * 状态 0-审核中 1-通过 2-拒绝
     */
    @ApiModelProperty(value = "状态 0-审核中 1-通过 2-拒绝")
    private Integer state;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankCard;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private String applyTime;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private String handleTime;
}
