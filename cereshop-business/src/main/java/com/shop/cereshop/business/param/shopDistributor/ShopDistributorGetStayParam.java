/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shopDistributor;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取待审核分销员列表请求
 */
@Data
@ApiModel(value = "DistributorGetCommissionParam", description = "获取总佣金详情请求")
public class ShopDistributorGetStayParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private String shopId;

    /**
     * 分销员名称
     */
    @ApiModelProperty(value = "分销员名称")
    private String distributorName;

    /**
     * 分销员手机号
     */
    @ApiModelProperty(value = "分销员手机号")
    private String distributorPhone;

    /**
     * 分销员等级id
     */
    @ApiModelProperty(value = "分销员等级id")
    private String distributorLevelId;

    /**
     * 申请时间数组
     */
    @ApiModelProperty(value = "申请时间数组")
    private List<String> dates;

    /**
     * 申请开始时间
     */
    @ApiModelProperty(value = "申请开始时间")
    private String startTime;

    /**
     * 申请结束时间
     */
    @ApiModelProperty(value = "申请结束时间")
    private String endTime;

    /**
     * 审核状态 0-待处理 1-审核通过 2-审核不通过
     */
    @ApiModelProperty(value = "审核状态 0-待处理 1-审核通过 2-审核不通过")
    private Integer state;
}
