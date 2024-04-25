/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取banner详情请求
 */
@Data
@ApiModel(value = "BannerGetByIdParam", description = "获取banner详情请求")
public class BannerGetByIdParam {

    /**
     * bannerid
     */
    @ApiModelProperty(value = "bannerid")
    private Long bannerId;
}
