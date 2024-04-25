/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_buyer_withdrawal 客户提现信息实体
 * @author 
 */
@Data
@ApiModel(value = "CereBuyerWithdrawal", description = "客户提现信息实体")
public class CereBuyerWithdrawal implements Serializable {
    /**
     * 提现申请id
     */
    @ApiModelProperty(value = "提现申请id")
    private Long withdrawalId;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "分销员id")
    private Long buyerUserId;

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
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalMoney;

    /**
     * 处理状态  0-审核中 1-通过 2-拒绝
     */
    @ApiModelProperty(value = "处理状态  0-审核中 1-通过 2-拒绝")
    private Integer state;

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

    private static final long serialVersionUID = 1L;

}
