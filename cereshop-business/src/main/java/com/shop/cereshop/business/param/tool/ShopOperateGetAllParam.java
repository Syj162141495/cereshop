/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取运营计划列表请求
 */
@Data
@ApiModel(value = "ShopOperateGetAllParam", description = "获取运营计划列表请求")
public class ShopOperateGetAllParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

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
     * 计划时间数组
     */
    @ApiModelProperty(value = "计划时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
           this.startTime=dates.get(0);
           this.endTime=dates.get(1);
        }
    }

    /**
     * 计划开始时间
     */
    private String startTime;

    /**
     * 计划结束时间
     */
    private String endTime;
}
