/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.tool;

import com.shop.cereshop.business.param.tool.CrowdCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 人群详情
 */
@Data
@ApiModel(value = "ShopCrowdDetail", description = "获取人群详情请求")
public class ShopCrowdDetail implements Serializable {

    /**
     * 店铺人群id
     */
    @ApiModelProperty(value = "店铺人群id")
    private Long shopCrowdId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

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
