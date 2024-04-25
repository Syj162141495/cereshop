/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景营销列表请求
 * @author
 */
@Data
@ApiModel(value = "ShopScene", description = "场景营销列表请求")
public class ShopScene {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 场景营销id
     */
    @ApiModelProperty(value = "场景营销id")
    private Long sceneId;

    /**
     * 营销类型 1-节日营销 2-会员日营销 3-生日营销
     */
    @ApiModelProperty(value = "营销类型 1-节日营销 2-会员日营销 3-生日营销")
    private Integer sceneType;

    /**
     * 营销名称
     */
    @ApiModelProperty(value = "营销名称")
    private String sceneName;

    /**
     * 时间描述
     */
    @ApiModelProperty(value = "时间描述")
    private String time;

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
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已停止")
    private Integer state;
}
