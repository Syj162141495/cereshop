/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.finance;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取财务列表请求
 */
@Data
@ApiModel(value = "FinanceParam", description = "获取财务列表请求")
public class FinanceParam extends PageParam {

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String shopCode;
}
