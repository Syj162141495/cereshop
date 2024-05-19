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

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品返回数据实体类
 */
@Data
@ApiModel(value = "ProductDetail", description = "商品返回数据实体类")
public class ProductDetail {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 关联店铺名称
     */
    @ApiModelProperty(value = "关联店铺名称")
    private String shopName;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品类型")
    private String productType;

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
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String shopGroupName;

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
     * 规格数据
     */
    @ApiModelProperty(value = "规格数据")
    private List<Sku> skus;

    /**
     * 商品图片第一张
     */
    @ApiModelProperty(value = "商品图片第一张")
    private String productImage;

    /**
     * 规格图片第一张
     */
    @ApiModelProperty(value = "规格图片第一张")
    private String skuImage;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    private BigDecimal price;

    /**
     * 原价
     */
    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Integer stockNumber;

    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Integer volume;

    /**
     * 规格名和值数据
     */
    @ApiModelProperty(value = "规格名和值数据")
    private List<SkuNameParam> names;

    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
    private Integer isRecommended;

    /**
     * 星级
     */
    @ApiModelProperty(value = "星级")
    private String starRating;

    /**
     * 面积
     */
    @ApiModelProperty(value = "面积")
    private String area;
}
