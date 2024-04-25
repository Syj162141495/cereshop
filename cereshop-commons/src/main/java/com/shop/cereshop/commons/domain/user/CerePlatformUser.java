/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_user 平台账户实体类
 * @author 
 */
@Data
@ApiModel(value = "CerePlatformUser", description = "平台账户实体类")
public class CerePlatformUser implements Serializable {

    /**
     * 账户id
     */
    @ApiModelProperty(value = "账户id")
    private Long platformUserId;

    /**
     * 平台登录账号
     */
    @ApiModelProperty(value = "平台登录账号")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 账户手机号
     */
    @ApiModelProperty(value = "账户手机号")
    private String phone;

    /**
     * 账户登录密码
     */
    @ApiModelProperty(value = "账户登录密码")
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 账户token
     */
    @ApiModelProperty(value = "账户token")
    private String token;

    /**
     * 是否启用   1-是 0-否
     */
    @ApiModelProperty(value = "是否启用   1-是 0-否")
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
