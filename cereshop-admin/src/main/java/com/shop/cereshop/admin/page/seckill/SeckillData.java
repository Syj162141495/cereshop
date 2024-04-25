/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.seckill;

import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 平台秒杀活动数据效果
 */
@Data
@ApiModel(value = "SeckillData", description = "平台秒杀活动数据效果")
public class SeckillData implements Serializable {
    /**
     * 参与商家数
     */
    @ApiModelProperty(value = "参与商家数")
    private Integer shops;

    /**
     * 参与商品数
     */
    @ApiModelProperty(value = "参与商品数")
    private Integer products;

    /**
     * 活动成交金额
     */
    @ApiModelProperty(value = "活动成交金额")
    private BigDecimal total;

    /**
     * 支付买家数
     */
    @ApiModelProperty(value = "支付买家数")
    private Integer pays;

    /**
     * 支付转化率
     */
    @ApiModelProperty(value = "支付转化率")
    private Integer conversion;

    /**
     * 支付订单数
     */
    @ApiModelProperty(value = "支付订单数")
    private Integer orders;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Integer visit;

    /**
     * 商家数据明细
     */
    @ApiModelProperty(value = "商家数据明细")
    private Page<ShopDataDetail> shopDataDetails;

    private static final long serialVersionUID = 1L;

}
