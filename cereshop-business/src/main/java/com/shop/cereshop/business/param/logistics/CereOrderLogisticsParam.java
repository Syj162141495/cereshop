/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.logistics;

import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import lombok.Data;

import java.util.List;

/**
 * 物流方案接收参数实体类
 */
@Data
public class CereOrderLogisticsParam {

    /**
     * 物流方案id
     */
    private Long logisticsId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 方案名称
     */
    private String logisticsName;

    /**
     * 计费方式 1-按件数 2-按重量 3-全国包邮
     */
    private Integer chargeType;

    /**
     * 计费明旭
     */
    private List<CereLogisticsCharge> charges;
}
