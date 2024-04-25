/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 物流方案返回数据实体类
 */
@Data
@ApiModel(value = "Logistics", description = "物流方案返回数据实体类")
public class Logistics {

    /**
     * 物流方案id
     */
    @ApiModelProperty(value = "物流方案id")
    private Long logisticsId;

    /**
     * 方案名称
     */
    @ApiModelProperty(value = "方案名称")
    private String logisticsName;

    /**
     * 计费方式 1-按件数 2-按重量 3-全国包邮
     */
    @ApiModelProperty(value = "计费方式 1-按件数 2-按重量 3-全国包邮")
    private Integer chargeType;

    /**
     * 配送范围
     */
    @ApiModelProperty(value = "配送范围")
    private String regions;

    /**
     * 计费明细
     */
    @ApiModelProperty(value = "计费明细")
    private List<Charge> charges;

    /**
     * 计费城市数组
     */
    private List<String> cities;
}
