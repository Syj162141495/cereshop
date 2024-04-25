/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_market_tool_detail  店铺营销工具优惠方案明细实体
 * @author 
 */
@Data
@ApiModel(value = "CereShopMarketToolDetail", description = "店铺营销工具优惠方案明细实体")
public class CereShopMarketToolDetail implements Serializable {
    /**
     * 关联店铺营销工具id
     */
    @ApiModelProperty(value = "关联店铺营销工具id")
    private Long toolId;

    /**
     * 满多少元
     */
    @ApiModelProperty(value = "满多少元")
    private BigDecimal fullMoney;

    /**
     * 减多少元
     */
    @ApiModelProperty(value = "减多少元")
    private BigDecimal reduceMoney;

    private static final long serialVersionUID = 1L;

}
