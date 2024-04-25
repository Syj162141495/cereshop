/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_platform_label 标签信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CerePlatformLabel", description = "标签信息实体类")
public class CerePlatformLabel implements Serializable {

    /**
     * 客户标签id
     */
    @ApiModelProperty(value = "客户标签id")
    private Long buyerLabelId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * 标签类型 1-手动标签 2-自动标签
     */
    @ApiModelProperty(value = "标签类型 1-手动标签 2-自动标签")
    private Integer labelType;

    /**
     * 满足条件 1-满足任意一个被选中条件即可  2-必须满足所有被选中条件
     */
    @ApiModelProperty(value = "满足条件 1-满足任意一个被选中条件即可  2-必须满足所有被选中条件")
    private Integer meetConditions;

    /**
     * 是否选中最后消费时间 1-是 0-否
     */
    @ApiModelProperty(value = "是否选中最后消费时间 1-是 0-否")
    private Integer lastConsumptionTime;

    /**
     * 是否选中累计消费次数  1-是 0-否
     */
    @ApiModelProperty(value = "是否选中累计消费次数  1-是 0-否")
    private Integer consumptionFrequency;

    /**
     * 是否选中累计交易金额 1-是 0-否
     */
    @ApiModelProperty(value = "是否选中累计交易金额 1-是 0-否")
    private Integer consumptionMoney;

    /**
     * 最近几天（天）
     */
    @ApiModelProperty(value = "最近几天（天）")
    private Integer consumptionDay;

    /**
     * 最后消费开始时间
     */
    @ApiModelProperty(value = "最后消费开始时间")
    private String consumptionStartTime;

    /**
     * 最后消费结束时间
     */
    @ApiModelProperty(value = "最后消费结束时间")
    private String consumptionEndTime;

    /**
     * 起始次数
     */
    @ApiModelProperty(value = "起始次数")
    private Integer frequencyStart;

    /**
     * 截止次数
     */
    @ApiModelProperty(value = "截止次数")
    private Integer frequencyEnd;

    /**
     * 起始金额
     */
    @ApiModelProperty(value = "起始金额")
    private BigDecimal moneyStart;

    /**
     * 截止金额
     */
    @ApiModelProperty(value = "截止金额")
    private BigDecimal moneyEnd;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
