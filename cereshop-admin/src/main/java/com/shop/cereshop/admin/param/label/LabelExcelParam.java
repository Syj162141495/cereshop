/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 导出标签请求
 */
@Data
@ApiModel(value = "LabelExcelParam", description = "导出标签请求")
public class LabelExcelParam {

    /**
     * 标签id数组
     */
    @ApiModelProperty(value = "标签id数组")
    private List<Long> ids;
}
