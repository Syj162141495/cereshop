/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.after;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取物流信息请求
 */
@Data
@ApiModel(value = "AfterGetDileveryParam", description = "获取物流信息请求")
public class AfterGetDileveryParam {

    /**
     * 物流公司
     */
    @ApiModelProperty(value = "物流公司")
    private String express;

    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号")
    private String deliverFormid;
}
