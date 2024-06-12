/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.order;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取订单列表请求
 */
@Data
@ApiModel(value = "OrderGetAllParam", description = "获取订单列表请求")
public class OrderGetAllParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 搜索类型  1-订单ID 2-买家账户 3-收件人姓名 4-收件人手机号 5-商品ID
     */
    @ApiModelProperty(value = "搜索类型  1-订单ID 2-买家账户 3-收件人姓名 4-收件人手机号 5-商品ID")
    private Integer searchType;

    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段")
    private String search;

    /**
     * 订单状态 1-待付款 2-待发货 3-已发货 4-已完成 5-已关闭
     */
    @ApiModelProperty(value = "订单状态 1-待付款 2-待发货 3-已发货 4-已完成 5-已关闭")
    private Integer state;

    /**
     * 售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭
     */
    @ApiModelProperty(value = "售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭")
    private Integer afterState;

    /**
     * 下单时间数组
     */
    @ApiModelProperty(value = "下单时间数组")
    private List<String> dates;

    /**
     * 下单开始时间
     */
    @ApiModelProperty(value = "下单开始时间")
    private String startTime;

    /**
     * 下单结束时间
     */
    @ApiModelProperty(value = "下单结束时间")
    private String endTime;

    /**
     * 检索类型：medical医疗；elderlyCare养老；rehabilitation康复（其他）
     */
    @ApiModelProperty(value = "检索类型")
    private String queryType;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }
}
