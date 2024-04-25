/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取订单详情请求
 */
@Data
@ApiModel(value = "OrderGetByIdParam", description = "获取订单详情请求")
public class OrderGetByIdParam {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;
}
