/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除字典请求
 */
@Data
@ApiModel(value = "DictDeleteParam", description = "删除字典请求")
public class DictDeleteParam {

    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    private Long dictId;
}
