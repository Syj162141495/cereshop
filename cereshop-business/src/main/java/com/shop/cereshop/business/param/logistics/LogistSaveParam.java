/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.logistics;

import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加物流方案请求
 */
@Data
@ApiModel(value = "LogistSaveParam", description = "添加物流方案请求")
public class LogistSaveParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 方案名称
     */
    @ApiModelProperty(value = "方案名称")
    @NotBlank(message = "方案名称不能为空")
    private String logisticsName;

    /**
     * 计费方式 1-按件数 2-按重量 3-全国包邮
     */
    @ApiModelProperty(value = "计费方式 1-按件数 2-按重量 3-全国包邮")
    private Integer chargeType;

    public void setChargeType(Integer chargeType) {
        if(EmptyUtils.isEmpty(chargeType)){
            //默认全国包邮
            this.chargeType = 3;
        }else {
            this.chargeType = chargeType;
        }
    }

    /**
     * 计费明旭
     */
    @ApiModelProperty(value = "计费明旭")
    private List<ChargeParam> charges;
}
