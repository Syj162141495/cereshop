/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户列表
 */
@Data
@ApiModel(value = "BuyerUser", description = "客户列表数据")
public class BuyerUser {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 客户昵称
     */
    @ApiModelProperty(value = "客户昵称")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 消费次数
     */
    @ApiModelProperty(value = "消费次数")
    private Integer frequency;

    /**
     * 累计消费金额
     */
    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal total;

    /**
     * 上次消费时间
     */
    @ApiModelProperty(value = "上次消费时间")
    private String lastTime;

    /**
     * 成为客户时间
     */
    @ApiModelProperty(value = "成为客户时间")
    private String time;

    /**
     * 客户关联标签
     */
    @ApiModelProperty(value = "客户关联标签")
    private String labelName;

    /**
     * 标签id数组
     */
    @ApiModelProperty(value = "标签id数组")
    private List<Long> labelIds;

    /**
     * 标签id拼接字符串
     */
    private String ids;
}
