/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.compose;

import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 获取组合捆绑详情请求
 * @author
 */
@Data
@ApiModel(value = "ComposeGetByIdParam", description = "获取组合捆绑详情请求")
public class ComposeGetByIdParam {

    /**
     * 组合id
     */
    @ApiModelProperty(value = "组合id")
    private Long composeId;

}
