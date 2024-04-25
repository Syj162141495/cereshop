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
import java.util.List;

/**
 * 财务统计返回数据实体类
 */
@Data
@ApiModel(value = "FinanceCount", description = "财务统计返回数据实体类")
public class FinanceCount {

    /**
     * 累计营业额
     */
    @ApiModelProperty(value = "累计营业额")
    private BigDecimal turnover;

    /**
     * 冻结金额
     */
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal frozenMoney;

    /**
     * 可提现金额
     */
    @ApiModelProperty(value = "可提现金额")
    private BigDecimal withdrawableMoney;

    /**
     * 提现中金额
     */
    @ApiModelProperty(value = "提现中金额")
    private BigDecimal withdrawableStayMoney;

    /**
     * 列表数据
     */
    @ApiModelProperty(value = "列表数据")
    private List<Finance> finances;
}
