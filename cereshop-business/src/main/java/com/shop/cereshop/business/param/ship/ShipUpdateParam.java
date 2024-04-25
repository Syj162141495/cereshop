/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.ship;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新关系设置请求
 */
@Data
@ApiModel(value = "ShipUpdateParam", description = "更新关系设置请求")
public class ShipUpdateParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 是否允许邀请下级  1-是 0-否
     */
    @ApiModelProperty(value = "是否允许邀请下级  1-是 0-否")
    private Integer ifInvitation;

    /**
     * 关系绑定有效期  1-永久有效 2-几天有效
     */
    @ApiModelProperty(value = "关系绑定有效期  1-永久有效 2-几天有效")
    private Integer bindValidity;

    /**
     * 有效天数
     */
    @ApiModelProperty(value = "有效天数")
    private Integer validityDay;

    /**
     * 抢客条件 1-随时可抢客 2-不允许抢客 3-保护期几天内不允许抢
     */
    @ApiModelProperty(value = "抢客条件 1-随时可抢客 2-不允许抢客 3-保护期几天内不允许抢")
    private Integer ifRobbing;

    /**
     * 保护期天数
     */
    @ApiModelProperty(value = "保护期天数")
    private Integer robbingDay;

    /**
     * 是否允许分销员之间建立客户关系 1-是 0-否
     */
    @ApiModelProperty(value = "是否允许分销员之间建立客户关系 1-是 0-否")
    private Integer ifDistributionRelationship;
}
