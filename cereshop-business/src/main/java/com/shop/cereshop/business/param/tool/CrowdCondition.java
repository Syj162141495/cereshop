/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 人群筛选条件
 */
@Data
@ApiModel(value = "CrowdCondition", description = "人群筛选条件")
public class CrowdCondition {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 类型 1-店铺有购买 2-店铺无购买 3-店铺有下单 4-店铺无下单
     * 5-店铺有加购 6-店铺无加购 7-店铺有访问 8-店铺无访问 9-有效购买次数
     * 10-有效购买金额 11-任意满足一个标签
     */
    @ApiModelProperty(value = "类型 1-店铺有购买 2-店铺无购买 3-店铺有下单 4-店铺无下单\n" +
            "5-店铺有加购 6-店铺无加购 7-店铺有访问 8-店铺无访问 9-有效购买次数\n" +
            "10-有效购买金额 11-任意满足一个标签")
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
     * 商家标签id数组
     */
    @ApiModelProperty(value = "商家标签id数组")
    private List<Long> labelIds;

    /**
     * 已满足部分条件的客户id数组
     */
    @ApiModelProperty(value = "已满足部分条件的客户id数组")
    private List<String> ids;

    /**
     * 需要过滤掉以前的客户id数组
     */
    private List<String> userIds;
}
