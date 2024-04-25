/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.activity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 营销活动接收参数实体类
 */
@Data
public class CereActivitySignParam {

    /**
     * 报名id
     */
    private Long signId;

    /**
     * 交易流水号
     */
    private String signCode;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 关联活动id
     */
    private Long activityId;

    /**
     * 保证金
     */
    private BigDecimal bondMoney;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动状态 0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束
     */
    private Integer state;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    private Integer examineState;

    /**
     * 商品id数组
     */
    private List<Long> ids;

    /**
     * 商品模糊搜索条件 1-商品id 2-商品名称
     */
    private Integer condition;

    /**
     * 搜索字段
     */
    private String search;

    /**
     * 分类id
     */
    private Long classifyId;

    /**
     * 商品分组id
     */
    private Long groupId;
}
