/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.discount;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台限时折扣活动商品查询请求
 */
@Data
@ApiModel(value = "DiscountGetProductsParam", description = "平台限时折扣活动商品查询请求")
public class DiscountGetProductsParam extends PageParam {

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

}
