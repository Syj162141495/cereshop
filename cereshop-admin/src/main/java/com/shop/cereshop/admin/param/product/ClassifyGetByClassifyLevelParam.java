/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品分类层级查询
 */
@Data
@ApiModel(value = "ClassifyGetByClassifyLevelParam", description = "获取固定层级分类请求")
public class ClassifyGetByClassifyLevelParam {
    @ApiModelProperty(value = "分类级别")
    private Integer classifyLevel;
}
