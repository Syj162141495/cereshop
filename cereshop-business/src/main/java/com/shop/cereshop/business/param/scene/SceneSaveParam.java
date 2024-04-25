/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加场景营销请求
 * @author
 */
@Data
@ApiModel(value = "SceneSaveParam", description = "添加场景营销请求")
public class SceneSaveParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 营销类型 1-节日营销 2-会员日营销 3-生日营销
     */
    @ApiModelProperty(value = "营销类型 1-节日营销 2-会员日营销 3-生日营销")
    @NotNull(message = "营销类型不能为空")
    private Integer sceneType;

    /**
     * 营销名称
     */
    @ApiModelProperty(value = "营销名称")
    @NotBlank(message = "营销名称不能为空")
    private String sceneName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /**
     * 营销规则 1-所有等级会员，同一规则 2-不同等级会员,不同规则
     */
    @ApiModelProperty(value = "营销规则 1-所有等级会员，同一规则 2-不同等级会员,不同规则")

    private Integer sceneRule;

    /**
     * 营销时间类型 1-每月 2-每周 3-每日 4-生日当天 5-生日当周 6-生日当月
     */
    @ApiModelProperty(value = "营销时间类型 1-每月 2-每周 3-每日 4-生日当天 5-生日当周 6-生日当月")
    private Integer sceneTimeType;

    /**
     * 营销事件 每月示例1-2表示每月一日到二日；每周示例1(周一)-2（周二）-3-4-5-6-7表示全选，0代表未选；每日示例2021-01-01 10:00:00至2021-01-01 12:00:00
     */
    @ApiModelProperty(value = "营销事件 每月示例1-2表示每月一日到二日；每周示例1(周一)-2（周二）-3-4-5-6-7表示全选，0代表未选；每日示例2021-01-01 10:00:00至2021-01-01 12:00:00")
    private String sceneTime;

    /**
     * 营销规则明细数据
     */
    @ApiModelProperty(value = "营销规则明细数据")
    private List<SceneMemberParam> memberParams;
}
