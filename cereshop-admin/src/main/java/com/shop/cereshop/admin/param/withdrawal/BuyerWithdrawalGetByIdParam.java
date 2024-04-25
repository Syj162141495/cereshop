/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.withdrawal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取用户提现申请详情
 */
@Data
@ApiModel(value = "BuyerWithdrawalGetByIdParam", description = "获取用户提现申请详情")
public class BuyerWithdrawalGetByIdParam {

    /**
     * 提现id
     */
    @ApiModelProperty(value = "提现id")
    private Long withdrawalId;

    /**
     * 处理状态 1-同意 2-拒绝
     */
    @ApiModelProperty(value = "处理状态 1-同意 2-拒绝")
    private Integer state;
}
