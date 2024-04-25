/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_discount 店铺限时折扣活动实体
 * @author 
 */
@Data
public class ShopDiscountGetByIdParam extends PageParam implements Serializable {
    /**
     * 店铺限时折扣活动id
     */
    @ApiModelProperty(value = "店铺限时折扣活动id")
    private Long shopDiscountId;
}
