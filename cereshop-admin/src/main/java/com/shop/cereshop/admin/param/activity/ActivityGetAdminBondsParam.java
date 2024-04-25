/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "GetAdminBondsParam", description = "保证金交易记录查询接收参数实体类")
public class ActivityGetAdminBondsParam extends PageParam {

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 保证金状态 1-冻结中 2-已退回
     */
    @ApiModelProperty(value = "保证金状态 1-冻结中 2-已退回")
    private Integer state;

    /**
     * 时间数组
     */
    @ApiModelProperty(value = "时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }

    /**
     * 开始时间条件
     */
    private String startTime;

    /**
     * 结束时间条件
     */
    private String endTime;
}
