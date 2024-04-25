/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.collage;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_collage_order_detail 拼单明细实体
 * @author 
 */
@Data
public class CereCollageOrderDetail implements Serializable {
    private Long id;

    /**
     * 拼团单id
     */
    private Long collageId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 状态   1-正常 0-失效订单
     */
    private Integer state;

    private static final long serialVersionUID = 1L;

}
