/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.activity;

import com.shop.cereshop.commons.domain.activity.CereShopMarketTool;
import com.shop.cereshop.commons.domain.activity.CereShopMarketToolDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 营销工具返回数据实体类
 */
@Data
@ApiModel(value = "MarketTool", description = "营销工具返回数据实体类")
public class MarketTool extends CereShopMarketTool {

    /**
     * 使用数量
     */
    @ApiModelProperty(value = "使用数量")
    private Integer use;

    /**
     * 领取数量
     */
    @ApiModelProperty(value = "领取数量")
    private Integer receive;

    /**
     * 优惠方案数据
     */
    @ApiModelProperty(value = "优惠方案数据")
    private List<CereShopMarketToolDetail> details;
}
