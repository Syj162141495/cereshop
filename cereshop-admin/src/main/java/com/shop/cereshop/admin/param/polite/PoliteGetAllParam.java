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
 * 查询支付有礼活动列表请求
 */
@Data
@ApiModel(value = "PoliteGetAllParam", description = "查询支付有礼活动列表请求")
public class PoliteGetAllParam extends PageParam implements Serializable {

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String politeName;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

    private static final long serialVersionUID = 1L;

}
