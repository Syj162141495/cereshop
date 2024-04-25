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
 * 启停用定价捆绑详情请求
 * @author
 */
@Data
@ApiModel(value = "PriceStartParam", description = "启停用定价捆绑详情请求")
public class PriceStartParam {

    /**
     * 定价id
     */
    @ApiModelProperty(value = "定价id")
    private Long priceId;

    /**
     * 状态 1-启用 3-已停用
     */
    @ApiModelProperty(value = "状态 1-启用 3-已停用")
    private Integer state;

}
