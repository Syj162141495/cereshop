/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.withdrawal;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取用户提现申请列表请求
 */
@Data
@ApiModel(value = "BuyerWithdrawalGetAllParam", description = "获取用户提现申请列表请求")
public class BuyerWithdrawalGetAllParam extends PageParam {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String name;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 提现状态 空-全部 0-待审核 1-通过 2-拒绝
     */
    @ApiModelProperty(value = "提现状态 空-全部 0-待审核 1-通过 2-拒绝")
    private Integer state;
}
