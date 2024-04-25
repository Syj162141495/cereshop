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
 * 获取字典详情请求
 */
@Data
@ApiModel(value = "DictGetByIdParam", description = "获取字典详情请求")
public class DictGetByIdParam {

    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    private Long dictId;
}
