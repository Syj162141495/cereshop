/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.ship;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 解除绑定请求
 */
@Data
@ApiModel(value = "ShipRelieveParam", description = "解除绑定请求")
public class ShipRelieveParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 关联分销员id
     */
    @ApiModelProperty(value = "关联分销员id")
    private Long distributorId;

    /**
     * 关联客户id
     */
    @ApiModelProperty(value = "关联客户id")
    private Long buyerUserId;
}
