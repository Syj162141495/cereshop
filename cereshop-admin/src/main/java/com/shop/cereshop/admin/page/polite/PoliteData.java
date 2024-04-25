/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.polite;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 活动数据
 */
@Data
@ApiModel(value = "PoliteData", description = "活动数据")
public class PoliteData implements Serializable {

    /**
     * 发放优惠券数量
     */
    @ApiModelProperty(value = "发放优惠券数量")
    private Integer number;

    /**
     * 领取礼品笔数
     */
    @ApiModelProperty(value = "领取礼品笔数")
    private Integer receives;

    /**
     * 领取礼品人数
     */
    @ApiModelProperty(value = "领取礼品人数")
    private Integer users;

    /**
     * 成长值发放
     */
    @ApiModelProperty(value = "成长值发放")
    private Integer growth;

    /**
     * 领取明细
     */
    @ApiModelProperty(value = "领取明细")
    private Page<PoliteDataDetail> details;

    private static final long serialVersionUID = 1L;

}
