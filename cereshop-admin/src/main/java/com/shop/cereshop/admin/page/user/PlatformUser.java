/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.user;

import com.shop.cereshop.admin.page.role.PlatformUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 账户返回数据实体类
 */
@Data
@ApiModel(value = "PlatformUser", description = "账户返回数据实体类")
public class PlatformUser {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long platformUserId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 状态 1-启用 0-停用
     */
    @ApiModelProperty(value = "状态 1-启用 0-停用")
    private Integer state;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

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
