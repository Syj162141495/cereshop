/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人群详情列表请求
 */
@Data
@ApiModel(value = "ShopCrowdGetAllParam", description = "获取人群列表请求")
public class ShopCrowdGetAllParam extends PageParam implements Serializable {

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
     * 客户数量最小值
     */
    @ApiModelProperty(value = "客户数量最小值")
    private Integer min;

    /**
     * 客户数量最大值
     */
    @ApiModelProperty(value = "客户数量最大值")
    private Integer max;

    private static final long serialVersionUID = 1L;

}
