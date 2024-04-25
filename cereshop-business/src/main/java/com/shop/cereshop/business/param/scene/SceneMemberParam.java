/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 场景营销规则会员等级
 * @author
 */
@Data
@ApiModel(value = "SceneMemberParam", description = "场景营销规则会员等级")
public class SceneMemberParam {

    /**
     * 会员等级id
     */
    @ApiModelProperty(value = "会员等级id")
    private Long memberLevelId;

    /**
     * 是否包邮 1-是 0-否
     */
    @ApiModelProperty(value = "是否包邮 1-是 0-否")
    private Integer ifFreeShipping;

    /**
     * 折扣
     */
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    /**
     * 优惠券则id数组
     */
    @ApiModelProperty(value = "优惠券则id数组")
    private List<Long> ids;
}
