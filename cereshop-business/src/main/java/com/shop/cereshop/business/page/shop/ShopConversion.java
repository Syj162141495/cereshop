/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺转化统计数据
 */
@Data
@ApiModel(value = "ShopConversion", description = "店铺转化统计数据")
public class ShopConversion {

    /**
     * 文字描述
     */
    @ApiModelProperty(value = "文字描述")
    private List<String> names;

    /**
     * 转化率
     */
    @ApiModelProperty(value = "转化率")
    private List<BigDecimal> rates;
}
