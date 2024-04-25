/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_withdrawal 商家提现申请实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopWithdrawal", description = "商家提现申请实体类")
public class CereShopWithdrawal implements Serializable {

    /**
     * 提现申请id
     */
    @ApiModelProperty(value = "提现申请id")
    private Long withdrawalId;

    /**
     * 关联店铺id
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

    /**
     * 处理状态  0-待处理 1-已处理
     */
    @ApiModelProperty(value = "处理状态  0-待处理 1-已处理")
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
