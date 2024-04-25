/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提现明细返回数据实体类
 */
@Data
@ApiModel(value = "WithdrawalDetail", description = "提现明细返回数据实体类")
public class WithdrawalDetail {

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalMoney;

    /**
     * 提现状态
     */
    @ApiModelProperty(value = "提现状态")
    private Integer state;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankCard;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    /**
     * 提现时间
     */
    @ApiModelProperty(value = "提现时间")
    private String applyTime;
}
