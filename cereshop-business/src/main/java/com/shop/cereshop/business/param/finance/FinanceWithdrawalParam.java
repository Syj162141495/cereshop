/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.finance;

import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取提现明细请求
 */
@Data
@ApiModel(value = "FinanceWithdrawalParam", description = "获取提现明细请求")
public class FinanceWithdrawalParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 提现时间数组
     */
    @ApiModelProperty(value = "时间")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime = dates.get(0);
            this.endTime = dates.get(1);
        }
    }

    /**
     * 提现开始时间
     */
    @ApiModelProperty(value = "提现开始时间")
    private String startTime;

    /**
     * 提现结束时间
     */
    @ApiModelProperty(value = "提现结束时间")
    private String endTime;
}
