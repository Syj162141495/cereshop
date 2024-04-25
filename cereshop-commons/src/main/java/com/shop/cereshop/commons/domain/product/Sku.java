/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import com.shop.cereshop.commons.domain.collage.CollageOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 规格数据
 */
@Data
@ApiModel(value = "Sku", description = "规格数据")
public class Sku implements Serializable {

    private static final long serialVersionUID = -6415941333616523286L;
    /**
     * 规格值code拼接字符串
     */
    @ApiModelProperty(value = "规格值code拼接字符串")
    private String valueCodes;

    /**
     * 规格id
     */
    @ApiModelProperty(value = "规格id")
    private Long skuId;

    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价")
    private BigDecimal originalPrice;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    private BigDecimal price;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String image;

    /**
     * 商品库存
     */
    @ApiModelProperty(value = "商品库存")
    private Integer stockNumber;

    /**
     * 所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价
     */
    @ApiModelProperty(value = "所属活动 0-常规商品 1-拼团活动 2-秒杀活动 3-限时折扣活动 4-平台秒杀 5-平台折扣 6-定价捆绑 7-组合捆绑 8-场景营销 9-会员价")
    private Integer activityType;

    /**
     * 店铺拼团活动id
     */
    @ApiModelProperty(value = "店铺拼团活动id")
    private Long shopGroupWorkId;

    /**
     * 秒杀活动id
     */
    @ApiModelProperty(value = "秒杀活动id")
    private Long shopSeckillId;

    /**
     * 限时折扣活动id
     */
    @ApiModelProperty(value = "限时折扣活动id")
    private Long shopDiscountId;

    /**
     * 秒杀活动id
     */
    @ApiModelProperty(value = "秒杀活动id")
    private Long platformSeckillId;

    /**
     * 平台限时折扣活动id
     */
    @ApiModelProperty(value = "平台限时折扣活动id")
    private Long platformDiscountId;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    /**
     * 活动结束倒计时
     */
    @ApiModelProperty(value = "活动结束倒计时")
    private long time=10000;

    /**
     * 是否正在进行活动预热 1-是 0-否
     */
    @ApiModelProperty(value = "是否正在进行活动预热 1-是 0-否 ")
    private Integer ifEnable;

    /**
     * 成团人数
     */
    @ApiModelProperty(value = "成团人数")
    private Integer person;

    /**
     * 拼单数据
     */
    @ApiModelProperty(value = "拼单数据")
    private List<CollageOrder> collageOrders;

    /**
     * 活动限制数量总数
     */
    @ApiModelProperty(value = "活动限制数量总数")
    private Integer total;
}
