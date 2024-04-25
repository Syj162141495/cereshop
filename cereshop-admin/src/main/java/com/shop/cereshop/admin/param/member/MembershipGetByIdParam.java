/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员权益详情请求
 */
@Data
@ApiModel(value = "MembershipGetByIdParam", description = "会员权益详情请求")
public class MembershipGetByIdParam {

    /**
     * 权益id
     */
    @ApiModelProperty(value = "权益id")
    private Long memberId;

}
