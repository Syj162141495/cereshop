/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 热卖商品数据实体类
 */
@Data
@ApiModel(value = "HotSellProduct", description = "热卖商品数据实体类")
@AllArgsConstructor
public class HotSellProduct {

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 已售件数
     */
    @ApiModelProperty(value = "已售件数")
    private Integer number;
}
