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
import java.math.BigDecimal;

/**
 * cere_shop_distribution_level 店铺分销员等级信息（最多5个）实体类
 * @author
 */
@Data
@ApiModel(value = "CereShopDistributionLevel", description = "店铺分销员等级信息（最多5个）实体类")
public class CereShopDistributionLevel implements Serializable {
    /**
     * 分销员等级id
     */
    @ApiModelProperty(value = "分销员等级id")
    private Long distributorLevelId;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称")
    private String levelName;

    /**
     * 等级编号-编号越大,等级越高
     */
    @ApiModelProperty(value = "等级编号-编号越大,等级越高")
    private Integer levelNum;

    /**
     * 是否开启自购分佣  1-是 0-否
     */
    @ApiModelProperty(value = "是否开启自购分佣  1-是 0-否")
    private Integer ifSelf;

    /**
     * 是否勾选累计分销金额 1-是 0-否
     */
    @ApiModelProperty(value = "是否勾选累计分销金额 1-是 0-否")
    private Integer ifMoney;

    /**
     * 是否勾选邀请下级满人数 1-是 0-否
     */
    @ApiModelProperty(value = "是否勾选邀请下级满人数 1-是 0-否")
    private Integer ifInvitation;

    /**
     * 是否勾选客户满人数 1-是 0-否
     */
    @ApiModelProperty(value = "是否勾选客户满人数 1-是 0-否")
    private Integer ifCustomer;

    /**
     * 累计分销金额
     */
    @ApiModelProperty(value = "累计分销金额")
    private BigDecimal conditionMoney;

    /**
     * 邀请下级满人数
     */
    @ApiModelProperty(value = "邀请下级满人数")
    private Integer conditionInvitation;

    /**
     * 客户满人数
     */
    @ApiModelProperty(value = "客户满人数")
    private Integer conditionCustomer;

    /**
     * 直接分佣比例(整数最大99)
     */
    @ApiModelProperty(value = "直接分佣比例(整数最大99)")
    private Integer directProportion;

    /**
     * 间接分佣比例(整数最大99)
     */
    @ApiModelProperty(value = "间接分佣比例(整数最大99)")
    private Integer indirectProportion;

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
