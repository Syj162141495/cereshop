/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivityDetail;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "GetShopsParam", description = "参与店铺数据管理查询接收参数实体类")
public class ActivityGetShopsParam extends PageParam {

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败")
    private Integer state;

    /**
     * 报名活动类型  1-平台优惠券 2-平台秒杀 3-平台限时折扣
     */
    @ApiModelProperty(value = "报名活动类型  1-平台优惠券 2-平台秒杀 3-平台限时折扣")
    private Integer signType;

}
