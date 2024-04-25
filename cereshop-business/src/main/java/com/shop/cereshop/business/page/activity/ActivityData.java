/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.activity;

import com.shop.cereshop.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 活动数据
 */
@Data
@ApiModel(value = "ActivityData", description = "活动数据")
public class ActivityData {

    /**
     * 成交笔数
     */
    @ApiModelProperty(value = "成交笔数")
    private Integer orders;

    /**
     * 成交金额
     */
    @ApiModelProperty(value = "成交金额")
    private BigDecimal total;

    /**
     * 成交人数
     */
    @ApiModelProperty(value = "成交人数")
    private Integer users;

    /**
     * 活动商品明细数据
     */
    @ApiModelProperty(value = "活动商品明细数据")
    private Page<ActivityProductData> datas;
}
