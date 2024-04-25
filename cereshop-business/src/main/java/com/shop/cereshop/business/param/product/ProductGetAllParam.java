/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取商品列表请求
 */
@Data
@ApiModel(value = "ProductGetAllParam", description = "获取商品列表请求")
public class ProductGetAllParam extends PageParam {

    /**
     * 搜做字段
     */
    @ApiModelProperty(value = "搜做字段")
    private String search;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @ApiModelProperty(value = "是否上架 1-上架 0-不上架")
    private Integer shelveState;

    /**
     * 库存状态 1-有库存 0-无库存
     */
    @ApiModelProperty(value = "库存状态 1-有库存 0-无库存")
    private Integer stock;

    /**
     * 关联分类id
     */
    @ApiModelProperty(value = "关联分类id")
    private Long classifyId;
}
