/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.distribution;

import com.shop.cereshop.commons.domain.distributor.CereDistributionOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销员订单返回数据实体类
 */
@Data
@ApiModel(value = "DistributionOrder", description = "分销员订单返回数据实体类")
public class DistributionOrder extends CereDistributionOrder {

    /**
     * 间接分销订单数据
     */
    @ApiModelProperty(value = "间接分销订单数据")
    private CereDistributionOrder child;
}
