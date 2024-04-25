/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.user;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取账户列表请求
 */
@Data
@ApiModel(value = "UserGetAllParam", description = "获取账户列表请求")
public class UserGetAllParam extends PageParam {

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

}
