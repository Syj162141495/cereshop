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
 * 获取素材信息请求
 */
@Data
@ApiModel(value = "LabelGetSourceParam", description = "获取素材信息请求")
public class LabelGetSourceParam {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;

    /**
     * 素材类型 1-图片 2-视频
     */
    @ApiModelProperty(value = "素材类型 1-图片 2-视频")
    private Integer labelType;
}
