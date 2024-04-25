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
 * 获取物流方案详情请求
 */
@Data
@ApiModel(value = "LogistGetByIdParam", description = "获取物流方案详情请求")
public class LogistGetByIdParam {

    /**
     * 物流方案id
     */
    @ApiModelProperty(value = "物流方案id")
    private Long logisticsId;
}
