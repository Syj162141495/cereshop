/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取财务统计数据请求
 */
@Data
@ApiModel(value = "FinanceCountParam", description = "获取财务统计数据请求")
public class FinanceCountParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 时间条件 1-日汇总 2-月汇总
     */
    @ApiModelProperty(value = "时间条件 1-日汇总 2-月汇总")
    private Integer condition;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String time;
}
