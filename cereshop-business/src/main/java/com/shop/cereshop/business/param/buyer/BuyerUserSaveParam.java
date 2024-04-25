/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 加标签
 */
@Data
@ApiModel(value = "BuyerUserSaveParam", description = "加标签请求")
public class BuyerUserSaveParam {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 标签id数组
     */
    @ApiModelProperty(value = "标签id数组")
    private List<Long> labelIds;
}
