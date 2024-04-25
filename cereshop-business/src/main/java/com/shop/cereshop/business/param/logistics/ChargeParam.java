/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物流明细请求参数
 * @author
 */
@Data
@ApiModel(value = "ChargeParam", description = "物流明细请求参数")
public class ChargeParam {

    /**
     * 配送区域  省-市-区
     */
    @ApiModelProperty(value = "配送区域  省-市-区")
    private List<String> regions;

    /**
     * 重量 多少KG/件
     */
    @ApiModelProperty(value = "重量 多少KG/件")
    private BigDecimal weight;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 续重 多少KG/件
     */
    @ApiModelProperty(value = "续重 多少KG/件")
    private BigDecimal secondWeight;

    /**
     * 续重价格
     */
    @ApiModelProperty(value = "续重价格")
    private BigDecimal secondPrice;
}
