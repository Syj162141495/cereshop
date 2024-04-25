/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.buyer;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取客户列表
 */
@Data
@ApiModel(value = "BuyerUserGetAllParam", description = "获取客户列表请求")
public class BuyerUserGetAllParam extends PageParam {

    /**
     * 上次消费时间数组
     */
    @ApiModelProperty(value = "上次消费时间数组")
    private List<String> lastTimes;

    public void setLastTimes(List<String> lastTimes) {
        if(!EmptyUtils.isEmpty(lastTimes)&&lastTimes.size()>1){
            this.lastStartTime = lastTimes.get(0);
            this.lastEndTime = lastTimes.get(1);
        }
    }

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }

    /**
     * 上次消费开始时间
     */
    private String lastStartTime;

    /**
     * 上次消费结束时间
     */
    private String lastEndTime;

    /**
     * 成为客户时间数组
     */
    @ApiModelProperty(value = "成为客户时间数组")
    private List<String> dates;

    /**
     * 成为客户开始时间
     */
    private String startTime;

    /**
     * 成为客户结束时间
     */
    private String endTime;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;
}
