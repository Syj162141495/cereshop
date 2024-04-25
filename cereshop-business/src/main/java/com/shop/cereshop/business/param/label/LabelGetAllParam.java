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
 * 获取标签列表请求
 */
@Data
@ApiModel(value = "LabelGetAllParam", description = "店铺id请求参数")
public class LabelGetAllParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 素材类型 1-图片 2-视频
     */
    @ApiModelProperty(value = "素材类型 1-图片 2-视频")
    private Integer labelType;
}
