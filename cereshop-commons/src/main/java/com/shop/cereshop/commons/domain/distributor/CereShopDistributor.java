/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.distributor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_distributor 分销员信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopDistributor", description = "分销员信息实体类")
public class CereShopDistributor implements Serializable {
    /**
     * 分销员id
     */
    @ApiModelProperty(value = "分销员id")
    private Long distributorId;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

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
     * 关联分销员等级id
     */
    @ApiModelProperty(value = "关联分销员等级id")
    private Long distributorLevelId;

    /**
     * 邀请人客户id
     */
    @ApiModelProperty(value = "邀请人客户id")
    private Long invitees;

    /**
     * 审核状态 0-待处理 1-审核通过 2-审核不通过
     */
    @ApiModelProperty(value = "审核状态 0-待处理 1-审核通过 2-审核不通过")
    private Integer state;

    /**
     * 是否清退 1-是 0-否
     */
    @ApiModelProperty(value = "是否清退 1-是 0-否")
    private Integer ifLiquidation;

    /**
     * 审核通过时间
     */
    @ApiModelProperty(value = "审核通过时间")
    private String joinTime;

    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码")
    private String InvitationCode;

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
