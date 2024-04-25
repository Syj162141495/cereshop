/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.finance;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取流水明细列表请求
 */
@Data
@ApiModel(value = "FinanceDetailParam", description = "获取流水明细列表请求")
public class FinanceDetailParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String time;

    /**
     * 收支类型 1-收入 2-支出
     */
    @ApiModelProperty(value = "收支类型 1-收入 2-支出")
    private Integer income;

    /**
     * 流水类型 传支付、退款、提现
     */
    @ApiModelProperty(value = "流水类型 传支付、退款、提现")
    private String state;
}
