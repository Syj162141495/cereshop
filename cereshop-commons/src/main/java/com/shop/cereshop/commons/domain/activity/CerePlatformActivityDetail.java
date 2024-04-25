/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.activity;

import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_platform_activity_detail  营销活动优惠方案明细实体类
 * @author
 */
@Data
@ApiModel(value = "CerePlatformActivityDetail", description = "营销活动优惠方案明细实体类")
public class CerePlatformActivityDetail implements Serializable {

    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    /**
     * 关联营销活动id
     */
    @ApiModelProperty(value = "关联营销活动id")
    private Long activityId;

    /**
     * 满多少元
     */
    @ApiModelProperty(value = "满多少元")
    @NotNull(message = "满减金额不能为空")
    private BigDecimal fullMoney;

    public void setFullMoney(BigDecimal fullMoney) {
        if(EmptyUtils.isEmpty(fullMoney)){
            this.fullMoney=new BigDecimal(0);
        }else {
            this.fullMoney = fullMoney;
        }
    }

    /**
     * 减多少元
     */
    @ApiModelProperty(value = "减多少元")
    @NotNull(message = "满减金额不能为空")
    private BigDecimal reduceMoney;

    private static final long serialVersionUID = 1L;

}
