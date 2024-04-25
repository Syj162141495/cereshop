/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.price;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取定价捆绑详情请求
 * @author
 */
@Data
@ApiModel(value = "PriceGetByIdParam", description = "获取定价捆绑详情请求")
public class PriceGetByIdParam {

    /**
     * 定价id
     */
    @ApiModelProperty(value = "定价id")
    private Long priceId;

}
