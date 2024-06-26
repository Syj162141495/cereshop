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
 * 删除标签请求
 */
@Data
@ApiModel(value = "LabelDeleteParam", description = "删除标签请求")
public class LabelDeleteParam {

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
}
