/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_operate 店铺运营计划实体
 * @author
 */
@Data
public class CereShopOperate implements Serializable {
    /**
     * 店铺运营计划id
     */
    private Long shopOperateId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 计划名称
     */
    private String operateName;

    /**
     * 店铺人群id
     */
    private Long shopCrowdId;

    /**
     * 计划方式  1-自动长期计划 2-手动定时计划
     */
    private Integer planMode;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    private Integer state;

    /**
     * 长期计划开始时间
     */
    private String planStart;

    /**
     * 长期计划结束时间
     */
    private String planEnd;

    /**
     * 手动定时执行时间，如果为空说明是立即执行
     */
    private String manualTime;

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
