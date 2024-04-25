/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 畅销商品排行榜数据
 */
@Data
@ApiModel(value = "ProductRanking", description = "畅销商品排行榜数据")
public class ProductRanking {

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 售卖数量
     */
    @ApiModelProperty(value = "售卖数量")
    private Integer total;
}
