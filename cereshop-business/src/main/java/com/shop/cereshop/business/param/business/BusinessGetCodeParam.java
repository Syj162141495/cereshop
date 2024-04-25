/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商家获取验证码请求
 */
@Data
@ApiModel(value = "BusinessGetCodeParam", description = "商家获取验证码请求")
public class BusinessGetCodeParam {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;
}
