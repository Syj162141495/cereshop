/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.user;

import com.shop.cereshop.business.page.role.PlatformUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商家用户数据
 * @author
 */
@Data
@ApiModel(value = "PlatformBusiness", description = "商家用户数据")
public class Business {

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
     * 是否启用 1-是 0-否
     */
    @ApiModelProperty(value = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 角色数据
     */
    @ApiModelProperty(value = "角色数据")
    private List<PlatformUserRole> roles;

    /**
     * 角色id数组
     */
    @ApiModelProperty(value = "角色id数组")
    private List<Long> ids;
}
