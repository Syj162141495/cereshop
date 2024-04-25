/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 添加提现申请请求
 */
@Data
@ApiModel(value = "FinanceSaveWithdralwalParam", description = "添加提现申请请求")
public class FinanceSaveWithdralwalParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

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
     * 收款人姓名
     */
    @ApiModelProperty(value = "收款人姓名")
    private String collectionName;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalMoney;
}
