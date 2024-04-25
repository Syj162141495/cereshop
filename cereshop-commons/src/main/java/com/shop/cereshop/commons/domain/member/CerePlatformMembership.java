/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_membership 会员权益信息实体
 * @author 
 */
@Data
public class CerePlatformMembership implements Serializable {
    /**
     * 会员权益id
     */
    @ApiModelProperty(value = "字典id")
    private Long memberId;

    /**
     * 权益名称
     */
    @ApiModelProperty(value = "字典id")
    private String memberName;

    /**
     * 权益图标地址
     */
    @ApiModelProperty(value = "字典id")
    private String memberIcon;

    /**
     * 权益说明
     */
    @ApiModelProperty(value = "字典id")
    private String memberReason;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
