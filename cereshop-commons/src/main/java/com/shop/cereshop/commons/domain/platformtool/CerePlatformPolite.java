/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.platformtool;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_platform_polite 平台支付有礼信息实体
 * @author 
 */
@Data
public class CerePlatformPolite implements Serializable {
    /**
     * 支付有礼活动id
     */
    private Long politeId;

    /**
     * 活动名称
     */
    private String politeName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 消费方式   1-购买一定金额商品 2-购买一定数量商品
     */
    private Integer buyerMode;

    /**
     * 满多少元/件,参与活动
     */
    private BigDecimal buyer;

    /**
     * 会员成长值
     */
    private Integer growth;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    private Integer state;

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
