/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.after;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

/**
 * 订单接收参数实体类
 */
@Data
public class CereOrderAfterParam extends PageParam {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 售后单id
     */
    private Long afterId;

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
     * 售后类型  1-仅退款  2-退货退款
     */
    private Integer afterType;

    /**
     * 商家处理留言
     */
    private String reason;

    /**
     * 快递公司
     */
    private Long express;

    /**
     * 快递单号
     */
    private String deliverFormid;

    /**
     * 列表累心 1-待商家处理 2-待商家收货
     */
    private Integer type;
}
