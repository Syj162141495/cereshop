/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.finance;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取保证金请求
 */
@Data
@ApiModel(value = "BondParam", description = "获取保证金请求")
public class BondParam extends PageParam {

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 保证金状态 空-全部 0-待支付 1-冻结中 2-已退回
     */
    @ApiModelProperty(value = "保证金状态 空-全部 0-待支付 1-冻结中 2-已退回")
    private Integer bondState;

    /**
     * 缴纳时间数组
     */
    @ApiModelProperty(value = "缴纳时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0)+" 00:00:00";
            this.endTime=dates.get(1)+" 23:59:59";
        }
    }

    /**
     * 缴纳开始时间
     */
    @ApiModelProperty(value = "申请开始时间")
    private String startTime;

    /**
     * 缴纳结束时间
     */
    @ApiModelProperty(value = "申请结束时间")
    private String endTime;
}
