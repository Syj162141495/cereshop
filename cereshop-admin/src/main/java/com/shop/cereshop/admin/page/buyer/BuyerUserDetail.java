/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.buyer;

import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户详情返回数据实体类
 */
@Data
@ApiModel(value = "BuyerUserDetail", description = "客户详情返回数据实体类")
public class BuyerUserDetail {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String name;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headImage;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

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
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    private String createTime;

    /**
     * 标签数据
     */
    @ApiModelProperty(value = "标签数据")
    private List<String> labels;

    /**
     * 下单数
     */
    @ApiModelProperty(value = "下单数")
    private Integer orders;

    /**
     * 支付成功数
     */
    @ApiModelProperty(value = "支付成功数")
    private Integer pays;

    /**
     * 购买商品数
     */
    @ApiModelProperty(value = "购买商品数")
    private Integer products;

    /**
     * 累计消费额
     */
    @ApiModelProperty(value = "累计消费额")
    private BigDecimal price;

    /**
     * 售后次数
     */
    @ApiModelProperty(value = "售后次数")
    private Integer afters;

    /**
     * 售后单数
     */
    @ApiModelProperty(value = "售后单数")
    private Integer afterOrders;

    /**
     * 售后成功单数
     */
    @ApiModelProperty(value = "售后成功单数")
    private Integer successAfters;

    /**
     * 订单列表
     */
    @ApiModelProperty(value = "订单列表")
    private List<BuyerOrder> orderList;

    /**
     * 评论列表
     */
    @ApiModelProperty(value = "评论列表")
    private List<BuyerComment> comments;

    /**
     * 收货地址列表
     */
    @ApiModelProperty(value = "收货地址列表")
    private List<CereBuyerReceive> receives;
}
