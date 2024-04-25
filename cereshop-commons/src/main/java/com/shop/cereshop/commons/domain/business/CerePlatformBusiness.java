/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_business 平台商家用户实体类
 * @author
 */
@Data
@ApiModel(value = "CerePlatformBusiness", description = "平台商家用户实体类")
public class CerePlatformBusiness implements Serializable {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商家用户id
     */
    @ApiModelProperty(value = "商家用户id")
    private Long businessUserId;

    /**
     * 商家登录账号
     */
    @ApiModelProperty(value = "商家登录账号")
    private String username;

    /**
     * 商家登录密码
     */
    @ApiModelProperty(value = "商家登录密码")
    private String password;

    /**
     * 商家昵称
     */
    @ApiModelProperty(value = "商家昵称")
    private String name;

    /**
     * 商家头像
     */
    @ApiModelProperty(value = "商家头像")
    private String avatar;

    /**
     * 商家性别
     */
    @ApiModelProperty(value = "商家性别")
    private String sex;

    /**
     * 商家邮箱
     */
    @ApiModelProperty(value = "商家邮箱")
    private String email;

    /**
     * 用户token
     */
    @ApiModelProperty(value = "用户token")
    private String token;

    /**
     * 是否启用 1-是 0-否
     */
    @ApiModelProperty(value = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
