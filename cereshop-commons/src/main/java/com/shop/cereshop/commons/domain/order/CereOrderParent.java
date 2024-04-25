/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_order_parent 父订单信息实体类
 * @author 
 */
@Data
public class CereOrderParent implements Serializable {
    /**
     * 父订单id
     */
    private Long parentId;

    /**
     * 父订单编号
     */
    private String parentFormid;

    /**
     * 所有子订单总价之和
     */
    private BigDecimal orderPrice;

    /**
     * 所有子订单物流费用之和
     */
    private BigDecimal logisticsPrice;

    /**
     * 所有子订单支付金额之和
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
