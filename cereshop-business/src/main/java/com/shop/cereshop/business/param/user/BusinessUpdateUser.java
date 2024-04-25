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
import java.util.List;

/**
 * 修改商家用户请求
 * @author 
 */
@Data
@ApiModel(value = "BusinessUpdateUser", description = "修改商家用户请求")
public class BusinessUpdateUser implements Serializable {
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
     * 角色id数组
     */
    @ApiModelProperty(value = "角色id数组")
    private List<Long> roleIds;

    private static final long serialVersionUID = 1L;

}
