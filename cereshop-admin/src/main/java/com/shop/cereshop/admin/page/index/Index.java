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
     * 新增服务商数
     * */
    @ApiModelProperty(value = "新增服务商数")
    private Integer newShopNum;

    /**
     * 待审核服务商数
     * */
    @ApiModelProperty(value = "待审核服务商数")
    private Integer unCheckShopNum;

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
     * 发布数
     * */
    @ApiModelProperty(value = "发布数")
    private Integer publishProductNum;

    /**
     * 未发布数
     * */
    @ApiModelProperty(value = "未发布数")
    private Integer unPublishProductNum;


    /**
     * 订单成交数
     * */
    @ApiModelProperty(value = "订单成交数")
    private Integer orderNum;

    /**
     * 本月新订单数
     * */
    @ApiModelProperty(value = "本月新订单数")
    private Integer newOrderNum;

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
     * 活跃用户人数
     * */
    @ApiModelProperty(value = "活跃用户人数")
    private Integer activityPersonNum;

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
