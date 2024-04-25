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
 * cere_order_reconciliation 订单对账单表(买家付款后生成和商家退款后生成)实体类
 * @author 
 */
@Data
public class CereOrderReconciliation implements Serializable {
    /**
     * 对账单id
     */
    private Long reconciliationId;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 状态  1-冻结 2-解冻
     */
    private Integer state;

    /**
     * 收支状态 1-收入 2-支出
     */
    private Integer type;

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
