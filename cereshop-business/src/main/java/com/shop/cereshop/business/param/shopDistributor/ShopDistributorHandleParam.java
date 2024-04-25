/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shopDistributor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销员处理请求
 */
@Data
@ApiModel(value = "ShopDistributorHandleParam", description = "分销员处理请求")
public class ShopDistributorHandleParam {

    /**
     * 分销员id
     */
    @ApiModelProperty(value = "分销员id")
    private Long distributorId;

    /**
     * 处理状态 1-审核通过 2-审核不通过
     */
    @ApiModelProperty(value = "处理状态 1-审核通过 2-审核不通过")
    private Integer state;
}
