/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除分组请求
 */
@Data
@ApiModel(value = "GroupDeleteParam", description = "删除分组请求")
public class GroupDeleteParam {

    /**
     * 商品分组id
     */
    @ApiModelProperty(value = "商品分组id")
    private Long shopGroupId;
}
