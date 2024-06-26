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
 * 获取提现详情请求
 */
@Data
@ApiModel(value = "WithdrawalGetByIdParam", description = "获取提现详情请求")
public class WithdrawalGetByIdParam {

    /**
     * 提现申请id
     */
    @ApiModelProperty(value = "提现申请id")
    private Long withdrawalId;
}
