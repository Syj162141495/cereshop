/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺满减券/折扣券列表
 */
@Data
@ApiModel(value = "ShopCouponGetAllParam", description = "获取店铺满减券/折扣券列表请求")
public class ShopCouponGetAllParam extends PageParam implements Serializable {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    /**
     * 优惠券类型 1-满减券 2-折扣券
     */
    @ApiModelProperty(value = "优惠券类型 1-满减券 2-折扣券")
    private Integer couponType;

    /**
     * 优惠券状态  0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "优惠券状态  0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 创建时间数组
     */
    @ApiModelProperty(value = "创建时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }

    /**
     * 创建时间开始时间
     */
    @ApiModelProperty(value = "创建时间开始时间")
    private String startTime;

    /**
     * 创建时间结束时间
     */
    @ApiModelProperty(value = "创建时间结束时间")
    private String endTime;

    private static final long serialVersionUID = 1L;

}
