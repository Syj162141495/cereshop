/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 添加banner请求
 */
@Data
@ApiModel(value = "BannerSaveParam", description = "添加banner请求")
public class BannerSaveParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 样式
     */
    @ApiModelProperty(value = "样式")
    private Integer bannerStyle;

    /**
     * 图片数组
     */
    @ApiModelProperty(value = "图片数组")
    private List<String> images;

    /**
     * 跳转地址
     */
    @ApiModelProperty(value = "跳转地址")
    private String bannerUrl;
}
