/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.image;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 图片数据
 */
@Data
@ApiModel(value = "ImageParam", description = "图片数据")
public class Image {

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    @NotBlank(message = "至少上传一张图片")
    private String imgPath;
}
