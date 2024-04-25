/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 更新物流方案请求
 */
@Data
@ApiModel(value = "LogistUpdateParam", description = "更新物流方案请求")
public class LogistUpdateParam {

    /**
     * 物流方案id
     */
    @ApiModelProperty(value = "物流方案id")
    private Long logisticsId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

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
     * 计费明旭
     */
    @ApiModelProperty(value = "计费明旭")
    private List<ChargeParam> charges;
}
