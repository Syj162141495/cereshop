/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.order;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import lombok.Data;

import java.util.List;

/**
 * 订单接收参数实体类
 */
@Data
public class CereShopOrderParam extends PageParam {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 搜索类型  1-订单ID 2-买家账户 3-收件人姓名 4-收件人手机号 5-商品ID
     */
    private Integer searchType;

    /**
     * 搜索字段
     */
    private String search;

    /**
     * 订单状态 1-待付款 2-待<if test="keyWord != null and keyWord!=''"> 3-已<if test="keyWord != null and keyWord!=''"> 4-已完成 5-已关闭
     */
    private Integer state;

    /**
     * 售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭
     */
    private Integer afterState;

    /**
     * 下单时间数组
     */
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }

    /**
     * 下单开始时间
     */
    private String startTime;

    /**
     * 下单结束时间
     */
    private String endTime;
}
