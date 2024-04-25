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
 * 人群id参数
 */
@Data
@ApiModel(value = "ShopCrowdGetByIdParam", description = "人群id参数")
public class ShopCrowdGetByIdParam implements Serializable {

    /**
     * 店铺人群id
     */
    @ApiModelProperty(value = "店铺人群id")
    private Long shopCrowdId;

    /**
     * 人群id数组
     */
    @ApiModelProperty(value = "人群id数组")
    private List<Long> ids;

    private static final long serialVersionUID = 1L;

}
