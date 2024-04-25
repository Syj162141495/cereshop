/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.finance;

import com.shop.cereshop.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 财务统计数据返回实体类
 */
@Data
@ApiModel(value = "FinanceCount", description = "财务统计数据返回实体类")
public class FinanceCount {

    /**
     * 营收总额
     */
    @ApiModelProperty(value = "营收总额")
    private BigDecimal revenue;

    /**
     * 冻结中金额
     */
    @ApiModelProperty(value = "冻结中金额")
    private BigDecimal frozen;

    /**
     * 可提现金额
     */
    @ApiModelProperty(value = "可提现金额")
    private BigDecimal withdrawable;

    /**
     * 已提现金额
     */
    @ApiModelProperty(value = "已提现金额")
    private BigDecimal haveWithdrawable;

    /**
     * 已退款金额
     */
    @ApiModelProperty(value = "已退款金额")
    private BigDecimal refund;

    /**
     * 列表数据
     */
    @ApiModelProperty(value = "列表数据")
    private Page<Finance> page;
}
