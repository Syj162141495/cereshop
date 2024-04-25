/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.distributorOrder;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取分销订单列表请求
 */
@Data
@ApiModel(value = "OrderGetAllParam", description = "获取分销订单列表请求")
public class OrderGetAllParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 分销员名称
     */
    @ApiModelProperty(value = "分销员名称")
    private String distributorName;

    /**
     * 分销员手机号
     */
    @ApiModelProperty(value = "分销员手机号")
    private String distributorPhone;

    /**
     * 交易时间数组（订单支付时间）
     */
    @ApiModelProperty(value = "交易时间数组（订单支付时间）")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0)+" 00:00:00";
            this.endTime=dates.get(1)+" 23:59:59";
        }
    }

    /**
     * 交易开始时间
     */
    @ApiModelProperty(value = "交易开始时间")
    private String startTime;

    /**
     * 交易结束时间
     */
    @ApiModelProperty(value = "交易结束时间")
    private String endTime;

    /**
     * 结算状态  1-已结算 0-未结算
     */
    @ApiModelProperty(value = "结算状态  1-已结算 0-未结算")
    private Integer state;
}
