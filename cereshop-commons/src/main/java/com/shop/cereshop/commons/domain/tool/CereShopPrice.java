/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_price 商家定价捆绑信息实体
 * @author 
 */
@Data
public class CereShopPrice implements Serializable {
    /**
     * 定价捆绑id
     */
    private Long priceId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 活动名称
     */
    private String composeName;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 状态 0-未开始 1-进行中 2-已结束 3-已停用
     */
    private Integer state;

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
