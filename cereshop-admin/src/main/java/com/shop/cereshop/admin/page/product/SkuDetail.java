/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 规格属性明细数据
 */
@Data
@ApiModel(value = "SkuDetail", description = "规格属性明细数据")
public class SkuDetail {

    /**
     * 规格名
     */
    private String skuName;

    /**
     * 规格值
     */
    private String skuValue;
}
