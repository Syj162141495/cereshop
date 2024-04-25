/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_operate_detail 店铺运营计划优惠券明细信息实体
 * @author 
 */
@Data
public class CereShopOperateDetail implements Serializable {
    private Long shopOperateId;

    /**
     * 店铺优惠券id
     */
    private Long shopCouponId;

    /**
     * 赠券数量
     */
    private Integer number;

    private static final long serialVersionUID = 1L;

}
