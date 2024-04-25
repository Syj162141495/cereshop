/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_recruit 店铺招募信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopRecruit", description = "店铺招募信息实体类")
public class CereShopRecruit implements Serializable {
    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 是否勾选购买任意商品  1-是 0-否
     */
    @ApiModelProperty(value = "是否勾选购买任意商品  1-是 0-否")
    private Integer purchaseEverything;

    /**
     * 是否勾选至少下单满多少  1-是 0-否
     */
    @ApiModelProperty(value = "是否勾选至少下单满多少  1-是 0-否")
    private Integer orderFrequency;

    /**
     * 是否勾选消费金额满多少 1-是 0-否
     */
    @ApiModelProperty(value = "是否勾选消费金额满多少 1-是 0-否")
    private Integer consumptionMoney;

    /**
     * 下单次数
     */
    @ApiModelProperty(value = "下单次数")
    private Integer frequency;

    /**
     * 消费金额
     */
    @ApiModelProperty(value = "消费金额")
    private BigDecimal money;

    /**
     * 是否需要审核 1-是 0-否
     */
    @ApiModelProperty(value = "是否需要审核 1-是 0-否")
    private Integer ifExamine;

    /**
     * 招募页链接
     */
    @ApiModelProperty(value = "招募页链接")
    private String url;

    private static final long serialVersionUID = 1L;

}
