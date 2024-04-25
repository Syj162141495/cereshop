/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 获取类别列表请求
 */
@Data
@ApiModel(value = "ClassifyGetAllParam", description = "获取类别列表请求")
public class ClassifyGetAllParam extends PageParam {
}
