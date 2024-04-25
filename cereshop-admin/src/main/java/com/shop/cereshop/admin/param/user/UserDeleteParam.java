/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除账户请求
 */
@Data
@ApiModel(value = "UserDeleteParam", description = "删除账户请求")
public class UserDeleteParam {

    /**
     * 账户id
     */
    @ApiModelProperty(value = "账户id")
    private Long platformUserId;
}
