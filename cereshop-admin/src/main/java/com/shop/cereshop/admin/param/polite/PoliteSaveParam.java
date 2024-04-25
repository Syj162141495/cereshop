/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.polite;

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
 * 新增平台支付有礼活动请求
 */
@Data
@ApiModel(value = "PoliteSaveParam", description = "新增平台支付有礼活动请求")
public class PoliteSaveParam implements Serializable {

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称",required = true)
    @NotBlank(message = "活动名称不能为空")
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
    @NotBlank(message = "活动开始时间不能为空")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    @NotBlank(message = "活动结束时间不能为空")
    private String endTime;

    /**
     * 消费方式   1-购买一定金额商品 2-购买一定数量商品
     */
    @ApiModelProperty(value = "消费方式   1-购买一定金额商品 2-购买一定数量商品")
    @NotNull(message = "消费方式不能为空")
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
    private List<CerePlatformPoliteActivity> details;

    private static final long serialVersionUID = 1L;

}
