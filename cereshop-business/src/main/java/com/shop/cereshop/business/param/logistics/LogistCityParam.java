/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取城市列表请求
 */
@Data
@ApiModel(value = "LogistCityParam", description = "获取城市列表请求")
public class LogistCityParam {

    /**
     * 父节点id  查省份传0
     */
    @ApiModelProperty(value = "父节点id  查省份传0")
    private Long cityPid;
}
