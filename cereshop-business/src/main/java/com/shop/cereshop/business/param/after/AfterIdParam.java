/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.after;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 售后处理请求
 */
@Data
@ApiModel(value = "AfterIdParam", description = "售后处理请求")
public class AfterIdParam {

    /**
     * 售后单id
     */
    @ApiModelProperty(value = "售后单id")
    private Long afterId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 商家处理留言
     */
    @ApiModelProperty(value = "商家处理留言")
    private String reason;

    /**
     * 快递公司
     */
    @ApiModelProperty(value = "快递公司")
    private Long express;

    /**
     * 快递单号
     */
    @ApiModelProperty(value = "快递单号")
    private String deliverFormid;
}
