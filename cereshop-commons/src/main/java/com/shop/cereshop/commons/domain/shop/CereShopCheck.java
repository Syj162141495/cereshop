/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_check 店铺入驻处理信息实体类
 * @author 
 */
@Data
public class CereShopCheck implements Serializable {

    /**
     * 处理id
     */
    private Long checkId;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 入住处理 1-统一入驻 0-拒绝入驻
     */
    private Integer checkHandle;

    /**
     * 处理原因
     */
    private String reason;

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
