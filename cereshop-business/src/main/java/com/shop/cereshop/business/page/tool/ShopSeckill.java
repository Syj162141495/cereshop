/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_discount 店铺秒杀活动列表
 * @author 
 */
@Data
@ApiModel(value = "ShopSeckill", description = "店铺秒杀活动列表返回数据")
public class ShopSeckill implements Serializable {
    /**
     * 店铺秒杀活动id
     */
    @ApiModelProperty(value = "店铺秒杀活动id")
    private Long shopSeckillId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String seckillName;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 活动内容
     */
    @ApiModelProperty(value = "活动内容")
    private String content;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private String effectiveStart;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private String effectiveEnd;

    /**
     * 直降多少元
     */
    @ApiModelProperty(value = "直降多少元")
    private BigDecimal downPrice;

    /**
     * 秒杀价
     */
    @ApiModelProperty(value = "秒杀价")
    private BigDecimal seckillPrice;

    private static final long serialVersionUID = 1L;
}
