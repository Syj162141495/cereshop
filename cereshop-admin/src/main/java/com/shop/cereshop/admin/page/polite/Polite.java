/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.polite;

import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 支付有礼活动列表数据
 */
@Data
@ApiModel(value = "Polite", description = "支付有礼活动列表数据")
public class Polite implements Serializable {

    /**
     * 平台支付有礼活动id
     */
    @ApiModelProperty(value = "平台支付有礼活动id")
    private Long politeId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String politeName;

    /**
     * 活动起始时间
     */
    @ApiModelProperty(value = "活动起始时间")
    private String time;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 消费方式
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

    private static final long serialVersionUID = 1L;

}
