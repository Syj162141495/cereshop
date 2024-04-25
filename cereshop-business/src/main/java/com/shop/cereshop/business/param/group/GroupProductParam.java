/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.group;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取分组商品列表请求
 */
@Data
@ApiModel(value = "GroupProductParam", description = "获取分组商品列表请求")
public class GroupProductParam extends PageParam {
    
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段")
    private String search;

    /**
     * 库存数量最小值
     */
    @ApiModelProperty(value = "库存数量最小值")
    private Integer minStock;

    /**
     * 库存数量最大值
     */
    @ApiModelProperty(value = "库存数量最大值")
    private Integer maxStock;

    /**
     * 价格最小值
     */
    @ApiModelProperty(value = "价格最小值")
    private BigDecimal minPrice;

    /**
     * 价格最大值
     */
    @ApiModelProperty(value = "价格最大值")
    private BigDecimal maxPrice;
}
