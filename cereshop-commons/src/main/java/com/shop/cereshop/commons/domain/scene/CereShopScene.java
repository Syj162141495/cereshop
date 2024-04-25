/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_scene 商家场景营销信息实体
 * @author 
 */
@Data
public class CereShopScene implements Serializable {
    /**
     * 场景营销id
     */
    private Long sceneId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 营销类型 1-节日营销 2-会员日营销 3-生日营销
     */
    private Integer sceneType;

    /**
     * 营销名称
     */
    private String sceneName;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 营销规则 1-所有等级会员，同一规则 2-不同等级会员,不同规则
     */
    private Integer sceneRule;

    /**
     * 营销时间类型 1-每月 2-每周 3-每日 4-生日当天 5-生日当周 6-生日当月
     */
    private Integer sceneTimeType;

    /**
     * 营销事件 每月示例1-2表示每月一日到二日；每周示例1(周一)-2（周二）-3-4-5-6-7表示全选，0代表未选；每日示例2021-01-01 10:00:00至2021-01-01 12:00:00
     */
    private String sceneTime;

    /**
     * 状态 0-未开始 1-进行中 2-已停止
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
