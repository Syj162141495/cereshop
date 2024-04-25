/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分类层级结构
 */
@Data
@ApiModel(value = "Classify", description = "分类层级结构")
public class Classify {

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long id;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Integer depth;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 分类图片
     */
    @ApiModelProperty(value = "分类图片")
    private String classifyImage;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<Classify> childs;
}
