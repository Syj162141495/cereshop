/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.page.buyer;

import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 我的信息
 */
@Data
@ApiModel(value = "MyUser", description = "我的信息")
public class MyUser extends CereBuyerUser {

    /**
     * 未读取消息总数
     */
    @ApiModelProperty(value = "未读取消息总数")
    private Integer notRead;

    /**
     * 待付款订单数
     */
    @ApiModelProperty(value = "待付款订单数")
    private Integer waitPayOrderCount;

    /**
     * 待发货订单数
     */
    @ApiModelProperty(value = "待发货订单数")
    private Integer waitSendOrderCount;

    /**
     * 待收货订单数
     */
    @ApiModelProperty(value = "待收货订单数")
    private Integer waitReceiveOrderCount;

    /**
     * 总订单数
     */
    @ApiModelProperty(value = "总订单数")
    private Integer totalOrderCount;


}
