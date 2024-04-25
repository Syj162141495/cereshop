/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "GetExaminesParam", description = "审核记录查询接收参数实体类")
public class ActivityGetExaminesParam extends PageParam {

    /**
     * 店铺id+活动id+报名id拼接字符串
     */
    @ApiModelProperty(value = "店铺id+活动id+报名id拼接字符串")
    private String only;

    /**
     * 活动类型 1-平台优惠券 2-平台秒杀 3-平台限时折扣
     */
    private Integer signType;
}
