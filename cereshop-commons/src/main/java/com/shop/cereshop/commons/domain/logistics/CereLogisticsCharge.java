/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_logistics_charge 物流计费明细信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereLogisticsCharge", description = "物流计费明细信息实体类")
public class CereLogisticsCharge implements Serializable {
    /**
     * 关联物流方案id
     */
    @ApiModelProperty(value = "关联物流方案id")
    private Long logisticsId;

    /**
     * 配送区域  省-市-区
     */
    @ApiModelProperty(value = "配送区域  省-市-区")
    private String region;

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

    private static final long serialVersionUID = 1L;

}
