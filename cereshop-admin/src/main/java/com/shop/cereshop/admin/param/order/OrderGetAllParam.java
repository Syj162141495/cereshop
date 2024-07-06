/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.order;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 订单列表查询请求
 */
@Data
@ApiModel(value = "OrderGetAllParam", description = "订单列表查询请求")
public class OrderGetAllParam extends PageParam {

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    private String shopName;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 用户
     */
    @ApiModelProperty(value = "用户")
    private String search;

    /**
     * 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消 6-待成团
     */
    @ApiModelProperty(value = "订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消 6-待成团")
    private Integer state;

    /**
     * 下单时间数组
     */
    @ApiModelProperty(value = "下单时间数组")
    private List<String> dates;

    /**
     * 发送开始时间
     */
    @ApiModelProperty(value = "发送开始时间")
    private String startTime;

    /**
     * 发送结束时间
     */
    @ApiModelProperty(value = "发送结束时间")
    private String endTime;

    /**
     * 检索类型：medical医疗；elderlyCare养老；rehabilitation康复（其他）
     */
    @ApiModelProperty(value = "检索类型")
    private String queryType;

    /**
     * 检索类型：三级服务分类ID
     */
    @ApiModelProperty(value = "三级服务分类ID")
    private String thirdClassifyID;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }
}
