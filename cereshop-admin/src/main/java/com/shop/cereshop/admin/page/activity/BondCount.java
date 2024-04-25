/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import com.shop.cereshop.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 保证金交易记录返回数据实体
 */
@Data
@ApiModel(value = "BondCount", description = "保证金交易记录返回数据实体")
public class BondCount {

    /**
     * 保证金总额
     */
    @ApiModelProperty(value = "保证金总额")
    private BigDecimal total;

    /**
     * 记录数据
     */
    @ApiModelProperty(value = "记录数据")
    private Page<ActivityBond> page;
}
