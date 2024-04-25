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
 * 运营计划详情
 */
@Data
@ApiModel(value = "ShopOperateGetByIdParam", description = "运营计划详情")
public class ShopOperateGetByIdParam implements Serializable {

    /**
     * 店铺运营计划id
     */
    @ApiModelProperty(value = "店铺运营计划id")
    private Long shopOperateId;

    /**
     * 店铺运营计划id数组
     */
    @ApiModelProperty(value = "店铺运营计划id数组")
    private List<Long> ids;

    private static final long serialVersionUID = 1L;
}
