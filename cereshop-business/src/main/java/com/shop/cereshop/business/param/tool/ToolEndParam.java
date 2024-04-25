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
 * 结束营销工具
 */
@Data
@ApiModel(value = "ToolEndParam", description = "结束营销工具")
public class ToolEndParam {

    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private Long toolId;
}
