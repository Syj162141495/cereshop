/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.polite;

import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 领取明细
 */
@Data
@ApiModel(value = "PoliteDataDetail", description = "领取明细")
public class PoliteDataDetail implements Serializable {

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private Integer name;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private Integer phone;

    /**
     * 获取优惠券数量
     */
    @ApiModelProperty(value = "获取优惠券数量")
    private Integer number;

    /**
     * 获取成长值
     */
    @ApiModelProperty(value = "获取成长值")
    private Integer growth;

    /**
     * 用户成交订单笔数
     */
    private Integer count;

    private static final long serialVersionUID = 1L;

}
