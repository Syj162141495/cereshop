/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import com.shop.cereshop.commons.domain.image.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 更新商品请求
 */
@Data
@ApiModel(value = "ProductUpdateParam", description = "更新商品请求")
public class ProductUpdateParam {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 卖点简介
     */
    @ApiModelProperty(value = "卖点简介")
    private String productBrief;

    /**
     * 关联商品分组id
     */
    @ApiModelProperty(value = "关联商品分组id")
    private Long shopGroupId;

    /**
     * 关联分类id
     */
    @ApiModelProperty(value = "关联分类id")
    private Long classifyId;

    /**
     * 关联供应商id
     */
    @ApiModelProperty(value = "关联供应商id")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 是否需要物流 1-是 0-否
     */
    @ApiModelProperty(value = "是否需要物流 1-是 0-否")
    private Integer ifLogistics;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @ApiModelProperty(value = "是否上架 1-上架 0-不上架")
    private Integer shelveState;

    /**
     * 是否允许超卖 1-是 0-否
     */
    @ApiModelProperty(value = "是否允许超卖 1-是 0-否")
    private Integer ifOversold;

    /**
     * 是否支持花呗分期 1-是 0-否
     */
    @ApiModelProperty(value = "是否支持花呗分期 1-是 0-否")
    private Integer ifHuabei;

    /**
     * 商品描述（富文本）
     */
    @ApiModelProperty(value = "商品描述（富文本）")
    private String productText;

    /**
     * 商品图片数组
     */
    @ApiModelProperty(value = "商品图片数组")
    private List<Image> images;

    /**
     * 规格名和值数据
     */
    @ApiModelProperty(value = "规格名和值数据")
    private List<SkuNameParam> names;

    /**
     * 规格数据
     */
    @ApiModelProperty(value = "规格数据")
    private List<SkuParam> skus;

    /**
     * 删除规格数据
     */
    @ApiModelProperty(value = "删除规格数据")
    private List<DeleteSkuParam> deletes;
}
