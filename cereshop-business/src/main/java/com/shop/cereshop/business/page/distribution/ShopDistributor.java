/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.distribution;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分销员返回数据实体类
 */
@Data
@ApiModel(value = "ShopDistributor", description = "分销员返回数据实体类")
public class ShopDistributor {

    /**
     * 邀请人id
     */
    @ApiModelProperty(value = "邀请人id")
    private Long invitees;

    /**
     * 分销员id
     */
    @ApiModelProperty(value = "分销员id")
    private Long distributorId;

    /**
     * 分销员昵称
     */
    @ApiModelProperty(value = "分销员昵称")
    private String distributorName;

    /**
     * 分销员手机号
     */
    @ApiModelProperty(value = "分销员手机号")
    private String distributorPhone;

    /**
     * 等级id
     */
    @ApiModelProperty(value = "等级id")
    private Long distributorLevelId;

    /**
     * 邀请人名称
     */
    @ApiModelProperty(value = "邀请人名称")
    private String inviteesName;

    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称")
    private String levelName;

    /**
     * 审核状态 0-待处理 1-审核通过 2-审核不通过
     */
    @ApiModelProperty(value = "审核状态 0-待处理 1-审核通过 2-审核不通过")
    private Integer state;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private String createTime;

    /**
     * 累计客户
     */
    @ApiModelProperty(value = "累计客户")
    private Integer total;

    /**
     * 累计下级数
     */
    @ApiModelProperty(value = "累计下级数")
    private Integer subordinate;

    /**
     * 类及佣金
     */
    @ApiModelProperty(value = "类及佣金")
    private BigDecimal money;

    /**
     * 累计下单数
     */
    @ApiModelProperty(value = "累计下单数")
    private Integer orderTotal;

    /**
     * 累计消费金额
     */
    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal orderMoney;
}
