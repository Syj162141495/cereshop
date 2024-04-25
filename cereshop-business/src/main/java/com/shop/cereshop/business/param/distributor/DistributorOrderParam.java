/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.distributor;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

import java.util.List;

/**
 * 分销订单接收参数实体类
 */
@Data
public class DistributorOrderParam extends PageParam {

    /**
     * 订单编号
     */
    private String orderFormid;

    /**
     * 分销员名称
     */
    private String distributorName;

    /**
     * 分销员手机号
     */
    private String distributorPhone;

    /**
     * 交易时间数组（订单支付时间）
     */
    private List<String> dates;

    /**
     * 交易开始时间
     */
    private String startTime;

    /**
     * 交易结束时间
     */
    private String endTime;

    /**
     * 结算状态  1-已结算 0-未结算
     */
    private Integer state;

    /**
     * 订单id数组
     */
    private Long ids;
}
