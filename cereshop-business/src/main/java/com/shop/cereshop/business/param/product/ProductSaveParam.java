/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import com.shop.cereshop.commons.domain.image.Image;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加商品请求
 */
@Data
@ApiModel(value = "ProductSaveParam", description = "添加商品请求")
public class ProductSaveParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品类型")
    @NotBlank(message = "商品类型不能为空")
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
    @NotNull(message = "分类不能为空")
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
    @NotBlank(message = "没有选择是否需要物流")
    private String ifLogistics;

    /**
     * 是否上架 1-上架 0-不上架
     */
    @ApiModelProperty(value = "是否上架 1-上架 0-不上架")
    @NotBlank(message = "没有选择是否上架")
    private String shelveState;

    /**
     * 是否允许超卖 1-是 0-否
     */
    @ApiModelProperty(value = "是否允许超卖 1-是 0-否")
    @NotBlank(message = "没有选择是否允许超卖")
    private String ifOversold;

    /**
     * 是否支持花呗分期 1-是 0-否
     */
    @ApiModelProperty(value = "是否支持花呗分期 1-是 0-否")
    @NotBlank(message = "没有选择是否支持花呗分期")
    private String ifHuabei;

    /**
     * 商品描述（富文本）
     */
    @ApiModelProperty(value = "商品描述（富文本）")
    private String productText;

    /**
     * 商品图片数组
     */
    @ApiModelProperty(value = "商品图片数组")
//    @Valid
    private List<Image> images;

    public void setImages(List<Image> images) {
        if(EmptyUtils.isEmpty(images)){
            List<Image> list=new ArrayList<>();
            Image image=new Image();
            image.setImgPath("");
            list.add(image);
            this.images=list;
        }else {
            this.images = images;
        }
    }

    /**
     * 规格名和值数据
     */
    @ApiModelProperty(value = "规格名和值数据")
    @Valid
    private List<SkuNameParam> names;

    /**
     * 规格数据
     */
    @ApiModelProperty(value = "规格数据")
    @Valid
    private List<SkuParam> skus;

    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
    @Valid
    private Integer isRecommended;

    /**
     * 星级
     */
    @ApiModelProperty(value = "星级")
    @Valid
    private String starRating;

    /**
     * 面积
     */
    @ApiModelProperty(value = "面积")
    @Valid
    private String area;
}
