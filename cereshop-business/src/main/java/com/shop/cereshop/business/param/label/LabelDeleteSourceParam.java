/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除素材图片请求
 */
@Data
@ApiModel(value = "LabelDeleteSourceParam", description = "删除素材图片请求")
public class LabelDeleteSourceParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String image;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String link;
}
