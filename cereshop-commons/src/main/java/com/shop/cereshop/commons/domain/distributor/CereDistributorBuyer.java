/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.distributor;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_distributor_buyer 分销员绑定客户信息实体类
 * @author 
 */
@Data
public class CereDistributorBuyer implements Serializable {
    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 关联分销员id
     */
    private Long distributorId;

    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 是否绑定 1-是 0-否
     */
    private Integer ifBind;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
