/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_user 客户信息实体类
 * @author
 */
@Data
@ApiModel(value = "CereBuyerUser", description = "客户信息实体类")
public class CereBuyerUser implements Serializable {

    /**
     * 客户id
     */
    private Long buyerUserId;

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
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private String birthday;

    /**
     * 微信openID
     */
    private String wechatOpenId;

    /**
     * 微信unionId
     */
    private String wechatUnionId;

    /**
     * 微信昵称
     */
    private String wechatName;

    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 支付宝小程序用户id
     */
    private String aliUserId;

    /**
     * 注册手机号
     */
    @ApiModelProperty(value = "注册手机号")
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像图片
     */
    @ApiModelProperty(value = "头像图片")
    private String headImage;

    /**
     * 是否启用   1-是 0-否
     */
    private Integer state;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    private Integer ifBlack;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求token
     */
    private String token;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 会员等级名称
     */
    private String memberLevelName;

    /**
     * 成长值
     */
    private Integer growth;

    /**
     * 下一级需要的成长值
     */
    private Integer nextLevelGrowth;

    /**
     * 下一级会员等级名称
     */
    private String nextLevelName;

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
