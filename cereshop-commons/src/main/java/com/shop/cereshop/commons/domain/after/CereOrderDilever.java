/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.after;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_order_dilever 订单发货实体
 * @author 
 */
@Data
public class CereOrderDilever implements Serializable {
    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 快递公司（取数据字典）
     */
    private Long express;

    /**
     * 快递单号
     */
    private String deliverFormid;

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
