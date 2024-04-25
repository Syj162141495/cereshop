/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.polite;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询支付有礼活动详情请求
 */
@Data
@ApiModel(value = "PoliteGetByIdParam", description = "查询支付有礼活动详情请求")
public class PoliteGetByIdParam extends PageParam implements Serializable {

    /**
     * 平台支付有礼活动id
     */
    @ApiModelProperty(value = "平台支付有礼活动id",required = true)
    private Long politeId;

    private static final long serialVersionUID = 1L;

}
