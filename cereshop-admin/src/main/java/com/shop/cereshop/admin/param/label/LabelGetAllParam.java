/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.label;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取标签列表请求
 */
@Data
@ApiModel(value = "LabelGetAllParam", description = "获取标签列表请求")
public class LabelGetAllParam extends PageParam {

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * 标签类型 1-手动标签 2-自动标签
     */
    @ApiModelProperty(value = "标签类型 1-手动标签 2-自动标签")
    private Integer labelType;
}
