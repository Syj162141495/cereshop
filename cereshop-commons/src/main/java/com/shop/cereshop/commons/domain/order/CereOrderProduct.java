/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_order_product   订单商品信息实体类
 * @author
 */
@Data
public class CereOrderProduct implements Serializable {

    /**
     * 订单商品明细id
     */
    private Long orderProductId;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 关联规格id
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 商品售价
     */
    private BigDecimal productPrice;

    /**
     * 图片地址
     */
    private String image;

    /**
     * SKU
     */
    private String SKU;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 活动id
     */
    private Long activityId;

    private static final long serialVersionUID = 1L;

}
