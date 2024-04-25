/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.ShopDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 活动详情数据返回实体类
 */
@Data
@ApiModel(value = "ActivityData", description = "活动详情数据返回实体类")
public class ActivityData {

    /**
     * 成交总额
     */
    @ApiModelProperty(value = "成交总额")
    private BigDecimal money;

    /**
     * 支付订单数
     */
    @ApiModelProperty(value = "支付订单数")
    private Integer orders;

    /**
     * 支付客户数
     */
    @ApiModelProperty(value = "支付客户数")
    private Integer users;

    /**
     * 参与商家数
     */
    @ApiModelProperty(value = "参与商家数")
    private Integer business;

    /**
     * 参与商品数
     */
    @ApiModelProperty(value = "参与商品数")
    private Integer products;

    /**
     * 分布情况
     */
    @ApiModelProperty(value = "分布情况")
    private List<Proportion> cities;

    /**
     * 前五省份访问占比
     */
    @ApiModelProperty(value = "前五省份访问占比")
    private List<Proportion> cityPeople;

    /**
     * 新老访客占比
     */
    @ApiModelProperty(value = "新老访客占比")
    private List<Proportion> newOlds;

    /**
     * 终端访客占比
     */
    @ApiModelProperty(value = "终端访客占比")
    private List<Proportion> terminals;

    /**
     * 系统访客占比
     */
    @ApiModelProperty(value = "系统访客占比")
    private List<Proportion> systems;

    /**
     * 趋势图
     */
    @ApiModelProperty(value = "趋势图")
    private Trend trend;

    /**
     * 商家成交排行榜
     */
    @ApiModelProperty(value = "商家成交排行榜")
    private List<ShopRanking> shopRankings;

    /**
     * 畅销商品排行榜
     */
    @ApiModelProperty(value = "畅销商品排行榜")
    private List<ProductRanking> productRankings;

    /**
     * 销售类别分布
     */
    @ApiModelProperty(value = "销售类别分布")
    private List<ActivityClassify> classifies;

    /**
     * 商家数据明细
     */
    @ApiModelProperty(value = "商家数据明细")
    private Page<ShopDetail> page;

    /**
     * 销售类别总销售额
     */
    @ApiModelProperty(value = "销售类别总销售额")
    private BigDecimal classifyMoney;
}
