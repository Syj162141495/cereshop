/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 运营计划选择优惠券请求
 */
@Data
@ApiModel(value = "OperateCouponParam", description = "运营计划选择优惠券请求")
public class OperateCouponParam extends PageParam implements Serializable {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 计划开始时间
     */
    @ApiModelProperty(value = "计划开始时间")
    private String startTime;

    /**
     * 计划结束时间
     */
    @ApiModelProperty(value = "计划结束时间")
    private String endTime;

    private static final long serialVersionUID = 1L;

}
