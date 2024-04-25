/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页概况请求
 */
@Data
@ApiModel(value = "IndexParam", description = "首页概况请求")
public class IndexParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 时间条件 1-今天 2-昨天 3-最近七天 4-最近30天
     */
    @ApiModelProperty(value = "时间条件 1-今天 2-昨天 3-最近七天 4-最近30天")
    private Integer condition;
}
