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
 * cere_activity_sign  商家报名活动申请信息实体类
 * @author 
 */
@Data
public class CereActivitySign implements Serializable {
    /**
     * 报名id
     */
    private Long signId;

    /**
     * 交易流水号
     */
    private String signCode;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 关联活动id
     */
    private Long activityId;

    /**
     * 保证金
     */
    private BigDecimal bondMoney;

    /**
     * 支付方式  1-微信支付 2-支付宝支付
     */
    private Integer paymentMode;

    /**
     * 收款二维码
     */
    private String qrImage;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    private Integer state;

    /**
     * 保证金状态 0-未支付 1-冻结中 2-已退回
     */
    private Integer bondState;

    /**
     * 缴纳时间
     */
    private String paymentTime;

    /**
     * 退保时间
     */
    private String returnTime;

    /**
     * 审核备注
     */
    private String remark;

    /**
     * 报名活动类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣
     */
    private Integer signType;

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
