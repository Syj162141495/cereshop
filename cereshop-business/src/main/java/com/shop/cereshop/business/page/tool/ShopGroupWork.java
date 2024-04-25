/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拼团活动列表
 */
@Data
@ApiModel(value = "ShopGroupWork", description = "拼团活动列表返回数据")
public class ShopGroupWork implements Serializable {
    /**
     * 店铺拼团活动id
     */
    @ApiModelProperty(value = "店铺拼团活动id")
    private Long shopGroupWorkId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String groupName;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 活动内容
     */
    @ApiModelProperty(value = "活动内容")
    private String content;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    private static final long serialVersionUID = 1L;

}
