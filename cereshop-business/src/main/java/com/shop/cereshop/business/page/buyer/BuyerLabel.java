/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户关联标签
 */
@Data
@ApiModel(value = "BuyerLabel", description = "客户关联标签")
public class BuyerLabel {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private String labelId;

    /**
     * 客户关联标签
     */
    @ApiModelProperty(value = "客户关联标签")
    private String labelName;
}
