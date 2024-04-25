/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import com.shop.cereshop.commons.domain.activity.CereShopMarketToolDetail;
import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺营销工具接收参数实体类
 */
@Data
public class CereShopMarketToolParam extends PageParam {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 优惠券id
     */
    private Long toolId;

    /**
     * 优惠券名称
     */
    private String toolName;

    /**
     * 优惠券类型 1-满减券 2-折扣券
     */
    private Integer toolType;

    /**
     * 发行数量
     */
    private Integer toolNumber;

    /**
     * 满多少元可用   null-无门槛
     */
    private BigDecimal threshold;

    /**
     * 优惠券内容
     */
    private String content;

    /**
     * 试用商品 null-全部商品  指定商品id（多个以逗号隔开）
     */
    private String trialProduct;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 优惠方式 1-满减 2-优惠券
     */
    private Integer discountMode;

    /**
     * 优惠方案  1-叠加优惠 2-阶梯优惠
     */
    private Integer discountProgramme;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 活动状态
     */
    private Integer state;

    /**
     * 优惠方案数据
     */
    private List<CereShopMarketToolDetail> details;
}
