/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 上传素材请求
 */
@Data
@ApiModel(value = "LabelSaveSourceParam", description = "上传素材请求")
public class LabelSaveSourceParam {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private List<String> images;

    /**
     * 素材地址
     */
    @ApiModelProperty(value = "素材地址")
    private String image;
}
