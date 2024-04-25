/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 修改运营计划
 */
@Data
@ApiModel(value = "ShopOperateSaveParam", description = "修改运营计划请求")
public class ShopOperateUpdateParam implements Serializable {
    /**
     * 店铺运营计划id
     */
    @ApiModelProperty(value = "店铺运营计划id")
    private Long shopOperateId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 计划名称
     */
    @ApiModelProperty(value = "计划名称")
    private String operateName;

    /**
     * 店铺人群id
     */
    @ApiModelProperty(value = "店铺人群id")
    private Long shopCrowdId;

    /**
     * 计划方式  1-自动长期计划 2-手动定时计划
     */
    @ApiModelProperty(value = "计划方式  1-自动长期计划 2-手动定时计划")
    private Integer planMode;

    /**
     * 长期计划开始时间
     */
    @ApiModelProperty(value = "长期计划开始时间")
    private String planStart;

    /**
     * 长期计划结束时间
     */
    @ApiModelProperty(value = "长期计划结束时间")
    private String planEnd;

    /**
     * 手动定时执行时间，如果为空说明是立即执行
     */
    @ApiModelProperty(value = "手动定时执行时间，如果为空说明是立即执行")
    private String manualTime;

    /**
     * 优惠券明细数据
     */
    @ApiModelProperty(value = "优惠券明细数据")
    private List<OperateCoupon> coupons;

    private static final long serialVersionUID = 1L;
}
