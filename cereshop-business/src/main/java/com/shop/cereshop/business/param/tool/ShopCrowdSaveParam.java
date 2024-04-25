/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增店铺人群
 */
@Data
@ApiModel(value = "ShopCrowdSaveParam", description = "新增店铺人群请求")
public class ShopCrowdSaveParam implements Serializable {

    /**
     * 人群名称
     */
    @ApiModelProperty(value = "人群名称")
    private String crowdName;

    /**
     * 筛选条件数组
     */
    @ApiModelProperty(value = "商家标签id数组")
    private List<CrowdCondition> conditions;

    private static final long serialVersionUID = 1L;

}
