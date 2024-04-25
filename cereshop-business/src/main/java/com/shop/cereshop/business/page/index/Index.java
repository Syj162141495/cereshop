/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.index;

import com.shop.cereshop.business.page.shop.ShopConversion;
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
     * 店铺访问人数
     */
    @ApiModelProperty(value = "店铺访问人数")
    private Integer total;

    /**
     * 待处理订单数
     */
    @ApiModelProperty(value = "待处理订单数")
    private Integer stayOrders;

    /**
     * 待处理售后订单数量
     */
    @ApiModelProperty(value = "待处理售后订单数量")
    private Integer stayAfters;

    /**
     * 成交金额
     */
    @ApiModelProperty(value = "成交金额")
    private BigDecimal money;

    /**
     * 当月每周访问用户数据
     */
    @ApiModelProperty(value = "当月每周访问用户数据")
    private VisitWeek visitWeek;

    /**
     * 订单转化漏斗数据
     */
    @ApiModelProperty(value = "订单转化漏斗数据")
    private ShopConversion conversion;

    /**
     * 热卖商品数据
     */
    @ApiModelProperty(value = "热卖商品数据")
    private List<HotSellProduct> hotSellProducts;

    /**
     * 总访问人数
     */
    @ApiModelProperty(value = "总访问人数")
    private Integer count;

    /**
     * 总转化率
     */
    @ApiModelProperty(value = "总转化率")
    private BigDecimal rate;
}
