/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 启停用商家请求
 */
@Data
@ApiModel(value = "ShopStartParam", description = "启停用商家请求")
public class ShopStartParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 是否启用 1-是 0-否
     */
    @ApiModelProperty(value = "是否启用 1-是 0-否")
    private Integer state;
}
