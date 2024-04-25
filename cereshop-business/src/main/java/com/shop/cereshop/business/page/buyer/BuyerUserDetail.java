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
 * 客户详情
 */
@Data
@ApiModel(value = "BuyerUserDetail", description = "客户详情数据")
public class BuyerUserDetail {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 客户昵称
     */
    @ApiModelProperty(value = "客户昵称")
    private String name;

    /**
     * 成为客户时间
     */
    @ApiModelProperty(value = "成为客户时间")
    private String time;

    /**
     * 来源方式
     */
    @ApiModelProperty(value = "来源方式")
    private String source;

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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 累计消费金额
     */
    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal total;

    /**
     * 累计消费订单数
     */
    @ApiModelProperty(value = "累计消费订单数")
    private Integer frequency;

    /**
     * 最近下单时间
     */
    @ApiModelProperty(value = "最近下单时间")
    private String lastTime;

    /**
     * 累计退款金额
     */
    @ApiModelProperty(value = "累计退款金额")
    private BigDecimal refundMoney;

    /**
     * 累计退款订单数
     */
    @ApiModelProperty(value = "累计退款订单数")
    private Integer refunds;

    /**
     * 关联标签字符串
     */
    @ApiModelProperty(value = "累计退款订单数")
    private String labelName;

    /**
     * 关联标签id数组
     */
    @ApiModelProperty(value = "关联标签id数组")
    private List<Long> ids;

}
