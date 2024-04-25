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
public class ShopDiscountGetAllParam extends PageParam implements Serializable {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private Long discountName;

    /**
     * 状态  0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "状态  0-未开始 1-进行中 2-已结束")
    private Integer state;
}
