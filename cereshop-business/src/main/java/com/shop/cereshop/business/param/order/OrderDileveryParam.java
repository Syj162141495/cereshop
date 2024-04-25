/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <if test="keyWord != null and keyWord!=''">请求
 */
@Data
@ApiModel(value = "OrderDileveryParam", description = "发货请求")
public class OrderDileveryParam {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 快递公司（取数据字典）
     */
    @ApiModelProperty(value = "快递公司（取数据字典）")
    private Long express;

    /**
     * 快递单号
     */
    @ApiModelProperty(value = "快递单号")
    private String deliverFormid;
}
