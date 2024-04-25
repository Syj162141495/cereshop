/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取商品列表请求
 */
@Data
@ApiModel(value = "ActivitySignGetProductParam", description = "获取商品列表请求")
public class ActivitySignGetProductParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 商品模糊搜索条件 1-商品id 2-商品名称
     */
    @ApiModelProperty(value = "商品模糊搜索条件 1-商品id 2-商品名称")
    private Integer condition;

    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段")
    private String search;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long classifyId;

    /**
     * 商品分组id
     */
    @ApiModelProperty(value = "商品分组id")
    private Long groupId;
}
