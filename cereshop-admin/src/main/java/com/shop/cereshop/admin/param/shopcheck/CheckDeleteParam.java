/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shopcheck;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除入驻审核记录请求
 */
@Data
@ApiModel(value = "CheckDeleteParam", description = "删除入驻审核记录请求")
public class CheckDeleteParam {

    /**
     * 处理id
     */
    @ApiModelProperty(value = "处理id")
    private Long checkId;
}
