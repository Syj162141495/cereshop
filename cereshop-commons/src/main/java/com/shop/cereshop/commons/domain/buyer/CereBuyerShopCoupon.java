/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_buyer_shop_coupon 客户关联商家优惠券实体
 * @author 
 */
@Data
public class CereBuyerShopCoupon implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 运营计划id
     */
    private Long shopOperateId;

    /**
     * 商家优惠券id
     */
    private Long shopCouponId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 用券开始时间
     */
    private String startTime;

    /**
     * 用券结束时间
     */
    private String endTime;

    /**
     * 优惠券类型 1-满减券 2-折扣券
     */
    private Integer couponType;

    /**
     * 状态 0-已领取 1-已使用 2-已过期
     */
    private Integer state;

    /**
     * 满多少元
     */
    private BigDecimal fullMoney;

    /**
     * 减多少元/打几折
     */
    private BigDecimal reduceMoney;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}
