/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除营销工具请求
 */
@Data
@ApiModel(value = "ToolDeleteParam", description = "删除营销工具请求")
public class ToolDeleteParam {

    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private Long toolId;
}
