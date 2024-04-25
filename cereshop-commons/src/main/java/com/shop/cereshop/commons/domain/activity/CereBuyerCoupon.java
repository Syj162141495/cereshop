/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.activity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_buyer_coupon  客户关联优惠券信息实体类
 * @author 
 */
@Data
public class CereBuyerCoupon implements Serializable {

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

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
     * 状态 0-已领取 1-已使用 2-已过期
     */
    private Integer state;

    /**
     * 满多少元
     */
    private BigDecimal fullMoney;

    /**
     * 减多少元
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
