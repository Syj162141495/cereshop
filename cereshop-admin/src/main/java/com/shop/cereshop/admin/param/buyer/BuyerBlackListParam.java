/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 贴标签请求
 */
@Data
@ApiModel(value = "BuyerSaveUserLabelParam", description = "贴标签请求")
public class BuyerBlackListParam {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    @ApiModelProperty(value = "是否加入黑名单 1-是 0-否")
    private Integer ifBlack;
}
