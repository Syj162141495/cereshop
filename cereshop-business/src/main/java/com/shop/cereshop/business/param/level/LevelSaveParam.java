/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.level;

import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 添加分销员等级请求
 */
@Data
@ApiModel(value = "LevelSaveParam", description = "添加分销员等级请求")
public class LevelSaveParam {

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
    @NotBlank(message = "等级名称不能为空")
    private String levelName;

    /**
     * 等级编号
     */
    @ApiModelProperty(value = "等级编号")
    @NotNull(message = "等级编号不能为空")
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
     * 选中条件数组
     */
    @ApiModelProperty(value = "选中条件数组")
    private List<Integer> conditions;

    public void setConditions(List<Integer> conditions) {
        if(!EmptyUtils.isEmpty(conditions)){
            if(conditions.contains(1)){
                this.ifMoney=1;
            }else {
                this.ifMoney=0;
            }
            if(conditions.contains(2)){
                this.ifInvitation=1;
            }else {
                this.ifInvitation=0;
            }
            if(conditions.contains(3)){
                this.ifCustomer=1;
            }else {
                this.ifCustomer=0;
            }
        }
    }

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
}
