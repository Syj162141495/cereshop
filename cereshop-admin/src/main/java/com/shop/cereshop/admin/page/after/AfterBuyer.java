/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.after;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 买家信息返回数据实体类
 */
@Data
@ApiModel(value = "AfterBuyer", description = "买家信息返回数据实体类")
public class AfterBuyer {

    /**
     * 买家账户
     */
    @ApiModelProperty(value = "买家账户")
    private String name;

    /**
     * 买家注册时间
     */
    @ApiModelProperty(value = "买家注册时间")
    private String registerTime;

    /**
     * 买家手机号
     */
    @ApiModelProperty(value = "买家手机号")
    private String phone;

    /**
     * 买家订单总数
     */
    @ApiModelProperty(value = "买家订单总数")
    private Integer orders;

    /**
     * 售后单数
     */
    @ApiModelProperty(value = "售后单数")
    private Integer afters;

    /**
     * 售后成功率
     */
    @ApiModelProperty(value = "售后成功率")
    private BigDecimal rate;
}
