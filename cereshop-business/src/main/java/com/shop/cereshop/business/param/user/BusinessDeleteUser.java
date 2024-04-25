/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除和修改查询商家用户请求
 * @author 
 */
@Data
@ApiModel(value = "BusinessDeleteUser", description = "删除和修改查询商家用户请求")
public class BusinessDeleteUser implements Serializable {
    /**
     * 商家用户id
     */
    @ApiModelProperty(value = "商家用户id")
    private Long businessUserId;

    private static final long serialVersionUID = 1L;

}
