/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.withdrawal;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取提现申请列表请求
 */
@Data
@ApiModel(value = "WithdrawalGetAllParam", description = "获取提现申请列表请求")
public class WithdrawalGetAllParam extends PageParam {

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
     * 提现日期
     */
    @ApiModelProperty(value = "提现日期")
    private String startTime;

    /**
     * 提现状态 1-已处理 0-未处理
     */
    @ApiModelProperty(value = "提现状态 1-已处理 0-未处理")
    private Integer state;
}
