/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除类别请求
 */
@Data
@ApiModel(value = "ClassifyDeleteParam", description = "删除类别请求")
public class ClassifyDeleteParam {

    /**
     * 一级类别id
     */
    @ApiModelProperty(value = "一级类别id")
    private Long oneClassifyId;
}
