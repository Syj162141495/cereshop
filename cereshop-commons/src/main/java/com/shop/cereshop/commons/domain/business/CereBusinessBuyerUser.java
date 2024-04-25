/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.business;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_business_buyer_user 商家用户关联实体类
 * @author
 */
@Data
public class CereBusinessBuyerUser implements Serializable {
    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户id
     */
    private Long buyerUserId;

    /**
     * 创建时间(首次成为空户时间)
     */
    private String createTime;

    /**
     * 更新时间(上次消费时间)
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
