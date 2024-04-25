/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 运营计划列表
 * @author
 */
@Data
@ApiModel(value = "ShopOperate", description = "运营计划列表")
public class ShopOperate {

    /**
     * 店铺运营计划id
     */
    @ApiModelProperty(value = "店铺运营计划id")
    private Long shopOperateId;

    /**
     * 计划名称
     */
    @ApiModelProperty(value = "计划名称")
    private String operateName;

    /**
     * 人群名称
     */
    @ApiModelProperty(value = "人群名称")
    private String crowdName;

    /**
     * 计划方式  1-自动长期计划 2-手动定时计划
     */
    @ApiModelProperty(value = "计划方式  1-自动长期计划 2-手动定时计划")
    private Integer planMode;

    /**
     * 长期计划开始时间
     */
    @ApiModelProperty(value = "长期计划开始时间")
    private String planStart;

    /**
     * 长期计划结束时间
     */
    @ApiModelProperty(value = "长期计划结束时间")
    private String planEnd;

    /**
     * 手动定时执行时间，如果为空说明是立即执行
     */
    @ApiModelProperty(value = "手动定时执行时间，如果为空说明是立即执行")
    private String manualTime;

    /**
     * 状态 0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

}
