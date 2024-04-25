/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import com.shop.cereshop.admin.page.product.ProductClassify;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 添加类别请求
 */
@Data
@ApiModel(value = "ClassifyParam", description = "添加类别请求")
public class ClassifyLevelParam {

    /**
     * 删除分类id数组
     */
    @ApiModelProperty(value = "删除分类id数组")
    private List<Long> deleteIds;

    /**
     * 分类层级数据
     */
    @ApiModelProperty(value = "分类层级数据")
    @Valid
    private List<ProductClassify> classifies;
}
