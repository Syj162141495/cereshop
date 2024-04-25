/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_compose 商家组合捆绑信息实体
 * @author 
 */
@Data
public class CereShopCompose implements Serializable {
    /**
     * 组合捆绑id
     */
    private Long composeId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 组合名称
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
     * 促销类型 1-直接定价 2-固定减价 3-折扣
     */
    private Integer composeType;

    /**
     * 促销值(元/折)
     */
    private BigDecimal promote;

    /**
     * 最终价格
     */
    private BigDecimal price;

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
