/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 店铺banner返回数据实体类
 */
@Data
@ApiModel(value = "ShopBanner", description = "店铺banner返回数据实体类")
public class ShopBanner {

    /**
     * banner id
     */
    @ApiModelProperty(value = "banner id")
    private Long bannerId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * banner图片地址
     */
    @ApiModelProperty(value = "banner图片地址")
    private String bannerImage;

    /**
     * 样式
     */
    @ApiModelProperty(value = "样式")
    private Integer bannerStyle;

    /**
     * 跳转地址
     */
    @ApiModelProperty(value = "跳转地址")
    private String bannerUrl;

    /**
     * 图片数组
     */
    @ApiModelProperty(value = "图片数组")
    private List<String> images;
}
