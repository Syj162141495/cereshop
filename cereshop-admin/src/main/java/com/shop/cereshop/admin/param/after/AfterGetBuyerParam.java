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
 * 获取买家信息请求
 */
@Data
@ApiModel(value = "GetBuyerParam", description = "获取买家信息请求")
public class AfterGetBuyerParam {

    /**
     * 买家账户id
     */
    @ApiModelProperty(value = "买家账户id")
    private Long buyerUserId;
}
