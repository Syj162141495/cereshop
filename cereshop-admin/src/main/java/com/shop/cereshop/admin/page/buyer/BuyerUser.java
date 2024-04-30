/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户返回数据实体类
 */
@Data
@ApiModel(value = "BuyerUser", description = "客户返回数据实体类")
public class BuyerUser {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long buyerUserId;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证")
    private String cid;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 会员等级名称
     */
    @ApiModelProperty(value = "会员等级名称")
    private String memberLevelName;

    /**
     * 消费总额
     */
    @ApiModelProperty(value = "消费总额")
    private BigDecimal total;

    /**
     * 购买次数
     */
    @ApiModelProperty(value = "购买次数")
    private Integer buyers;

    /**
     * 最近消费时间
     */
    @ApiModelProperty(value = "最近消费时间")
    private String time;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    private String createTime;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    @ApiModelProperty(value = "是否加入黑名单 1-是 0-否")
    private Integer ifBlack;
}
