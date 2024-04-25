/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.activity.CereShopMarketToolDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 添加营销工具请求
 */
@Data
@ApiModel(value = "ToolSaveParam", description = "添加营销工具请求")
public class ToolSaveParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String toolName;

    /**
     * 优惠券类型 1-满减券 2-折扣券
     */
    @ApiModelProperty(value = "优惠券类型 1-满减券 2-折扣券")
    private Integer toolType;

    /**
     * 发行数量
     */
    @ApiModelProperty(value = "发行数量")
    private Integer toolNumber;

    /**
     * 满多少元可用   null-无门槛
     */
    @ApiModelProperty(value = "满多少元可用   null-无门槛")
    private BigDecimal threshold;

    /**
     * 优惠券内容
     */
    @ApiModelProperty(value = "优惠券内容")
    private String content;

    /**
     * 试用商品 null-全部商品  指定商品id（多个以逗号隔开）
     */
    @ApiModelProperty(value = "试用商品 null-全部商品  指定商品id（多个以逗号隔开）")
    private String trialProduct;

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
     * 优惠方式 1-满减 2-优惠券
     */
    @ApiModelProperty(value = "优惠方式 1-满减 2-优惠券")
    private Integer discountMode;

    /**
     * 优惠方案  1-叠加优惠 2-阶梯优惠
     */
    @ApiModelProperty(value = "优惠方案  1-叠加优惠 2-阶梯优惠")
    private Integer discountProgramme;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String image;

    /**
     * 优惠方案数据
     */
    @ApiModelProperty(value = "优惠方案数据")
    private List<CereShopMarketToolDetail> details;
}
