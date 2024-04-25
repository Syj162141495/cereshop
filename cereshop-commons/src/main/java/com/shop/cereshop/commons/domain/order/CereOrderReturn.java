/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.order;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_order_return 退货单信息实体类
 * @author 
 */
@Data
public class CereOrderReturn implements Serializable {
    /**
     * 退货单id
     */
    private Long returnId;

    /**
     * 退货单号
     */
    private String returnFormid;

    /**
     * 关联订单id
     */
    private Long orderId;

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
