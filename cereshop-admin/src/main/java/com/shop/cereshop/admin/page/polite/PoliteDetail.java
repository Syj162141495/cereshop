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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 支付有礼活动详情数据
 */
@Data
@ApiModel(value = "PoliteDetail", description = "支付有礼活动详情数据")
public class PoliteDetail implements Serializable {

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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
     * 消费方式   1-购买一定金额商品 2-购买一定数量商品
     */
    @ApiModelProperty(value = "消费方式   1-购买一定金额商品 2-购买一定数量商品")
    private Integer buyerMode;

    /**
     * 满多少元/件,参与活动
     */
    @ApiModelProperty(value = "满多少元/件,参与活动")
    private BigDecimal buyer;

    /**
     * 会员成长值
     */
    @ApiModelProperty(value = "会员成长值")
    private Integer growth;

    /**
     * 选择优惠前数据
     */
    @ApiModelProperty(value = "选择优惠前数据")
    private List<PoliteActivityDetail> details;

    private static final long serialVersionUID = 1L;

}
