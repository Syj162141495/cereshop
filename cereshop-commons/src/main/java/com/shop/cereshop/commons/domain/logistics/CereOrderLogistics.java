/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.logistics;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_order_logistics 物流方案设置信息实体类
 * @author 
 */
@Data
public class CereOrderLogistics implements Serializable {
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
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
