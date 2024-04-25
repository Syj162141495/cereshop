/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.compose;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 启停用组合捆绑详情请求
 * @author
 */
@Data
@ApiModel(value = "ComposeStartParam", description = "启停用组合捆绑详情请求")
public class ComposeStartParam {

    /**
     * 组合id
     */
    @ApiModelProperty(value = "组合id")
    private Long composeId;

    /**
     * 状态 1-启用 3-已停用
     */
    @ApiModelProperty(value = "状态 1-启用 3-已停用")
    private Integer state;

}
