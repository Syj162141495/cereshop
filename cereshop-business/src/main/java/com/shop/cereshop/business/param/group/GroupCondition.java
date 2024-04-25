/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 智能添加条件
 */
@Data
@ApiModel(value = "GroupCondition", description = "智能添加条件")
public class GroupCondition {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 类型 1-库存 2-价格 3-重量 4-销量
     */
    @ApiModelProperty(value = "类型 1-库存 2-价格 3-重量 4-销量")
    private Integer type;

    /**
     * 运算符 1-大于 2-等于 3-小于
     */
    @ApiModelProperty(value = "运算符 1-大于 2-等于 3-小于")
    private Integer calculation;

    /**
     * 数值
     */
    @ApiModelProperty(value = "数值")
    private BigDecimal number;

    /**
     * 已满足部分条件的商品id数组
     */
    @ApiModelProperty(value = "已满足部分条件的商品id数组")
    private List<Long> ids;
}
