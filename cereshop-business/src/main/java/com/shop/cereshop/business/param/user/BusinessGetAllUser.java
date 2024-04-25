/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.user;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取用户列表请求
 * @author 
 */
@Data
@ApiModel(value = "BusinessGetAllUser", description = "获取用户列表请求")
public class BusinessGetAllUser extends PageParam implements Serializable {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "搜索字段")
    private Long shopId;

    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段")
    private String search;

    /**
     * 是否启用 1-是 0-否
     */
    @ApiModelProperty(value = "是否启用 1-是 0-否")
    private Integer state;

    private static final long serialVersionUID = 1L;

}
