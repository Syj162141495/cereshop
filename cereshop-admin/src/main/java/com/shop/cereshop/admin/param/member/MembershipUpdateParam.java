/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 编辑会员权益请求
 */
@Data
@ApiModel(value = "MembershipUpdateParam", description = "编辑会员权益请求")
public class MembershipUpdateParam {

    /**
     * 权益id
     */
    @ApiModelProperty(value = "权益id")
    private Long memberId;

    /**
     * 权益名称
     */
    @ApiModelProperty(value = "权益名称")
    private String memberName;

    /**
     * 权益图标
     */
    @ApiModelProperty(value = "权益图标")
    private String memberIcon;

    /**
     * 权益说明
     */
    @ApiModelProperty(value = "权益说明")
    private String memberReason;

}
