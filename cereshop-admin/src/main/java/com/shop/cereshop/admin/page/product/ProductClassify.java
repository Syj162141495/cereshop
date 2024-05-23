/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import com.shop.cereshop.commons.domain.image.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 商品类别返回数据实体类
 */
@Data
@ApiModel(value = "ProductClassify", description = "商品类别返回数据实体类")
public class ProductClassify {

    /**
     * 一级类别id
     */
    @ApiModelProperty(value = "一级类别id")
    private Long id;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    @NotBlank(message = "类别名称不能为空")
    private String categoryName;

    /**
     * 类别链接
     */
    @ApiModelProperty(value = "类别链接")
    private Long link;

    /**
     * 类别图片地址
     */
    @ApiModelProperty(value = "类别图片地址")
    private String categoryImg;

    /**
     * 类别图片地址
     */
    @ApiModelProperty(value = "类别图片地址")
    private List<Image> categoryImgArray;

    /**
     * 类别级别
     */
    @ApiModelProperty(value = "类别级别")
    private Integer depth;

    /**
     * 子节点数据
     */
    @ApiModelProperty(value = "子节点数据")
    private List<ProductClassify> childs;


    /**
     * 类别介绍
     */
    @ApiModelProperty(value = "类别介绍")
    private String description;
}
