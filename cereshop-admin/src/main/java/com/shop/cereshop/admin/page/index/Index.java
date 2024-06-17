/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 首页概况返回数据实体类
 */
@Data
@ApiModel(value = "Index", description = "首页概况返回数据实体类")
public class Index {
    /**
     * 服务商总数
     * */
    @ApiModelProperty(value = "服务商总数")
    private Integer shopNum;

    /**
     * 客户数量
     * */
    @ApiModelProperty(value = "客户数量")
    private Integer personNum;

    /**
     * 服务分类项数
     * */
    @ApiModelProperty(value = "服务分类项数")
    private Integer classifyNum;

    /**
     * 服务项数
     * */
    @ApiModelProperty(value = "服务项数")
    private Integer productNum;

    /**
     * 订单成交数
     * */
    @ApiModelProperty(value = "订单成交数")
    private Integer orderNum;

    /**
     * 总成交额
     * */
    @ApiModelProperty(value = "总成交额")
    private BigDecimal moneyNum;

    /**
     * 当月新用户
     * */
    @ApiModelProperty(value = "当月新用户")
    private Integer newPersonNum;

    /**
     * 最新订单
     * */
    @ApiModelProperty(value = "最新订单")
    private List<OrderInfo> orderList;

    /**
     * echart 数据1
     * */
    @ApiModelProperty(value = "echart数据1")
    private MoneyTotal moneyTotal;

    /**
     * echart 数据2
     * */
    @ApiModelProperty(value = "echart数据2")
    private PersonTotal personTotal;

}
