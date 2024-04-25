/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_seckill 店铺秒杀活动实体
 * @author 
 */
@Data
public class CereShopSeckill implements Serializable {
    /**
     * 店铺秒杀活动id
     */
    private Long shopSeckillId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 活动名称
     */
    private String seckillName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 开始时间
     */
    private String effectiveStart;

    /**
     * 结束时间
     */
    private String effectiveEnd;

    /**
     * 商品限购 1-不限购 2-限购
     */
    private Integer ifLimit;

    /**
     * 限购几件/人
     */
    private Integer limitNumber;

    /**
     * 是否限量 1-是 0-否
     */
    private Integer ifNumber;

    /**
     * 限制数量
     */
    private Integer number;

    /**
     * 活动预热   1-停用  2-启用
     */
    private Integer ifEnable;

    /**
     * 预热几小时前
     */
    private Integer enableTime;

    /**
     * 优惠券是否叠加 1-是 0-否
     */
    private Integer ifAdd;

    /**
     * 活动状态 0-未开始 1-进行中 2-已结束
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
