/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.ship;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取关系设置列表请求
 */
@Data
@ApiModel(value = "ShipGetAllParam", description = "获取关系设置列表请求")
public class ShipGetAllParam extends PageParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 客户昵称
     */
    @ApiModelProperty(value = "客户昵称")
    private String name;

    /**
     * 客户手机号
     */
    @ApiModelProperty(value = "客户手机号")
    private String phone;

    /**
     * 分销员昵称
     */
    @ApiModelProperty(value = "分销员昵称")
    private String distributorName;

    /**
     * 绑定状态 是否绑定 1-是 0-否
     */
    @ApiModelProperty(value = "绑定状态 是否绑定 1-是 0-否")
    private Integer ifBind;
}
